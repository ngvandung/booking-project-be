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

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.BookingBusiness;
import com.booking.business.VnPayPaymentBusiness;
import com.booking.constant.BookingConstant;
import com.booking.exception.BadRequestException;
import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.model.User;
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

		// Neu doi tuong nay dang cho thue thi khong duoc thue nua
		List<Booking> bookings = bookingService.findBookings(className, classPK, null, null, BookingConstant.RENTING,
				null);
		if (bookings != null && !bookings.isEmpty()) {
			throw new BadRequestException();
		}

		double totalAmount = 0;
		QueryUtil queryUtil = BeanUtil.getBean(QueryUtil.class);
		Map<String, Object> map = queryUtil.getByClassPK_ClassName(classPK, className);
		if (map != null && map.size() > 0) {
			double bookingTime = RentUtil.calculateRentTime(fromDate, toDate);
			double price = Double.valueOf(String.valueOf(map.get("price")));
			totalAmount = bookingTime * price;
		}

		long _userId = 0;
		try {
			_userId = userContext.getUser().getUserId();
		} catch (Exception e) {
			// nothing to do
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

}
