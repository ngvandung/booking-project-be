/**
 * 
 */
package com.booking.business.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.BookingBusiness;
import com.booking.business.VnPayPaymentBusiness;
import com.booking.constant.BookingConstant;
import com.booking.exception.BadRequestException;
import com.booking.exception.ForbiddenException;
import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.model.User;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.BookingService;
import com.booking.service.HomeService;
import com.booking.service.UserService;
import com.booking.util.BeanUtil;
import com.booking.util.ConfigVnPay;
import com.booking.util.DateFormat;
import com.booking.util.QueryUtil;
import com.booking.util.RentUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class BookingBusinessImpl implements BookingBusiness {

	private static final Logger _log = Logger.getLogger(BookingBusinessImpl.class);
			
	@Autowired
	private BookingService bookingService;
	@Autowired
	private HomeService homeService;
	@Autowired
	private UserService userService;
	@Autowired
	private VnPayPaymentBusiness vnPayPaymentBusiness;

	@Override
	public Booking findById(long bookingId) {
		return bookingService.findById(bookingId);
	}

	@Override
	public Map<String, Object> createBooking(HttpServletRequest request, HttpServletResponse response,
			int numberOfGuest, Date fromDate, Date toDate, long classPK, String className, String fullName,
			String email, String phone, long stateId, String stateName, String vnpOrderInfo, String orderType,
			String bankCode, String language, UserContext userContext) throws IOException {

		Map<String, Object> result = new HashMap<String, Object>();
		QueryUtil queryUtil = BeanUtil.getBean(QueryUtil.class);
		Map<String, Object> map = queryUtil.getByClassPK_ClassName(classPK, className);

		long _userId = 0;
		try {
			_userId = userContext.getUser().getUserId();
		} catch (Exception e) {
			// nothing to do
			System.out.println(e);
		}

		// Neu nguoi thue la chu nha thi khong duoc thue
		if (_userId != 0) {
			if (Long.valueOf(String.valueOf(map.get("ownerId"))) == _userId) {
				throw new ForbiddenException();
			}
		}

		// Neu doi tuong nay dang cho thue thi khong duoc thue nua
		List<Booking> bookings = bookingService.checkTime(classPK, className, fromDate, toDate,
				BookingConstant.RENTING);
		if (bookings != null && !bookings.isEmpty()) {
			throw new BadRequestException();
		}

		double totalAmount = 0;
		if (map != null && map.size() > 0) {
			double bookingTime = RentUtil.calculateRentTime(fromDate, toDate);
			double price = Double.valueOf(String.valueOf(map.get("price")));
			totalAmount = bookingTime * price;
		} else {
			throw new BadRequestException();
		}

		String ipAddress = ConfigVnPay.getIpAddress(request);
		Booking booking = bookingService.createBooking(numberOfGuest, fromDate, toDate, classPK, className, totalAmount,
				BookingConstant.PAYING, fullName, email, phone, stateId, stateName, ipAddress, _userId);
		if (booking != null) {
			if (className.equals(Home.class.getName())) {
				Home home = homeService.findById(classPK);

				// Thanh toan vnpay
				long userId = home.getUserId();
				User user = userService.findByUserId(userId);
				String vnp_TmnCode = user.getTmnCode();
				String vnp_hashSecret = user.getHashSecret();

				result = vnPayPaymentBusiness.payment(request, response, vnpOrderInfo, orderType, bankCode, language,
						vnp_TmnCode, vnp_hashSecret, totalAmount, booking.getBookingId());
			}
		}
		return result;
	}

	@Override
	public Booking deleteBooking(long bookingId, UserContext userContext) {
		return bookingService.deleteBooking(bookingId);
	}

	@Override
	public double calculatorPrice(long classPK, String className, String fromDate, String toDate)
			throws ParseException {
		Date dFDate = DateFormat.formatDate(fromDate);
		Date dTDate = DateFormat.formatDate(toDate);
		QueryUtil queryUtil = BeanUtil.getBean(QueryUtil.class);
		Map<String, Object> map = queryUtil.getByClassPK_ClassName(classPK, className);
		double totalAmount = 0;

		if (map != null && map.size() > 0) {
			double bookingTime = RentUtil.calculateRentTime(dFDate, dTDate);
			double price = Double.valueOf(String.valueOf(map.get("price")));
			totalAmount = bookingTime * price;
		}

		return totalAmount;
	}

	@Override
	public List<Booking> findByToDate(Date now, String bookingStatus) {
		return bookingService.findByToDate(now, bookingStatus);
	}

	@Override
	public List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId) {
		return bookingService.findBookings(className, classPK, totalAmount, numberOfGuest, bookingStatus, userId);
	}

	@Override
	public List<Booking> checkTime(Long classPK, String className, String fromDate, String toDate)
			throws ParseException {
		Date _fromDate = DateFormat.formatDate(fromDate);
		Date _toDate = DateFormat.formatDate(toDate);
		return bookingService.checkTime(classPK, className, _fromDate, _toDate, BookingConstant.RENTING);
	}

	@Override
	public List<Map<String, Object>> findMyBookings(long userId, String className, String bookingStatus,
			UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return bookingService.findMyBookings(userId, className, bookingStatus);
	}

	@Override
	public List<Map<String, Object>> findDetailBookings(long ownerId, Long classPK, String className,
			String bookingStatus, UserContext userContext) {
		if (PermissionCheckerFactoryUtil.isOwner(userContext, ownerId)) {
			return bookingService.findDetailBookings(ownerId, classPK, className, bookingStatus);
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public Booking cancelRequestBooking(long bookingId, UserContext userContext) {
		Booking booking = bookingService.findById(bookingId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, booking.getUserId())) {
			if (booking.getBookingStatus().equals(BookingConstant.RENTING)) {
				booking.setBookingStatus(BookingConstant.CANCEL_PENDING);
				booking.setModifiedDate(new Date());
				booking.setUserId(userContext.getUser().getUserId());

				return bookingService.updateBooking(booking);
			} else {
				throw new BadRequestException();
			}
		} else {
			throw new ForbiddenException();
		}

	}

	@Override
	public Booking cancelActionBooking(long bookingId, String bookingStatus, UserContext userContext) {
		Booking booking = bookingService.findById(bookingId);
		// Validate owner
		long ownerId = 0;
		if (booking.getClassName().equals(Home.class.getName())) {
			Home home = homeService.findById(booking.getClassPK());
			ownerId = home.getOwnerHomeId();
		}

		if (PermissionCheckerFactoryUtil.isOwner(userContext, ownerId)) {
			if (booking.getBookingStatus().equals(BookingConstant.CANCEL_PENDING)) {
				booking.setBookingStatus(bookingStatus);
				booking.setModifiedDate(new Date());
				
				//TODO: Hoan tien cho khach hàng
				//........

				return bookingService.updateBooking(booking);
			} else {
				throw new BadRequestException();
			}
		} else {
			throw new ForbiddenException();
		}
	}

}
