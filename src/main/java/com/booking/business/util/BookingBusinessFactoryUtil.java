/**
 * 
 */
package com.booking.business.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.booking.business.BookingBusiness;
import com.booking.model.Booking;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class BookingBusinessFactoryUtil {
	// Design pattern - Singleton
	public static BookingBusiness _bookingBusiness;

	public static BookingBusiness getBookingBusiness() {

		if (_bookingBusiness == null) {
			_bookingBusiness = BeanUtil.getBean(BookingBusiness.class);
		}
		return _bookingBusiness;
	} // ============================

	public static Booking findById(long rentId) {
		return getBookingBusiness().findById(rentId);
	}

	public static Map<String, Object> createBooking(HttpServletRequest request, HttpServletResponse response,
			int numberOfGuest, Date fromDate, Date toDate, long classPK, String className, String fullName,
			String email, String phone, long stateId, String stateName, String vnpOrderInfo, String orderType,
			String bankCode, String language, UserContext userContext)
			throws UnsupportedEncodingException, IOException {
		return getBookingBusiness().createBooking(request, response, numberOfGuest, fromDate, toDate, classPK,
				className, fullName, email, phone, stateId, stateName, vnpOrderInfo, orderType, bankCode, language,
				userContext);
	}

	public static Booking deleteBooking(long rentId, UserContext userContext) {
		return getBookingBusiness().deleteBooking(rentId, userContext);
	}

	public static double calculatorPrice(long classPK, String className, String fromDate, String toDate)
			throws ParseException {
		return getBookingBusiness().calculatorPrice(classPK, className, fromDate, toDate);
	}

	public static List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId) {
		return getBookingBusiness().findBookings(className, classPK, totalAmount, numberOfGuest, bookingStatus, userId);
	}

	public static List<Booking> checkTime(Long classPK, String className, String fromDate, String toDate)
			throws ParseException {
		return getBookingBusiness().checkTime(classPK, className, fromDate, toDate);
	}

	public static List<Map<String, Object>> findMyBookings(long userId, String className, String bookingStatus,
			UserContext userContext) {
		return getBookingBusiness().findMyBookings(userId, className, bookingStatus, userContext);
	}

	public static List<Map<String, Object>> findDetailBookings(long ownerId, Long classPK, String className,
			String bookingStatus, UserContext userContext) {
		return getBookingBusiness().findDetailBookings(ownerId, classPK, className, bookingStatus, userContext);
	}

	public static Booking cancelRequestBooking(long bookingId, UserContext userContext) {
		return getBookingBusiness().cancelRequestBooking(bookingId, userContext);
	}

	public static Booking cancelActionBooking(long bookingId, String bookingStatus, UserContext userContext) {
		return getBookingBusiness().cancelActionBooking(bookingId, bookingStatus, userContext);
	}
}
