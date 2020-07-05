/**
 * 
 */
package com.booking.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.BookingBusinessFactoryUtil;
import com.booking.business.util.VnPayPaymentBusinessFactoryUtil;
import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.util.BeanUtil;
import com.booking.util.DateFormat;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class BookingController {
	@RequestMapping(value = "/bookings", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Booking> bookingHome(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(name = "className", required = false) String className,
			@RequestParam(name = "classPK", required = false) Long classPK,
			@RequestParam(name = "numberOfGuest", required = false) Integer numberOfGuest,
			@RequestParam(name = "totalAmount", required = false) Double totalAmount,
			@RequestParam(name = "userId", required = false) Long userId,
			@RequestParam(name = "bookingStatus", required = false) String bookingStatus)
			throws UnsupportedEncodingException, IOException {

		return BookingBusinessFactoryUtil.findBookings(className, classPK, totalAmount, numberOfGuest, bookingStatus,
				userId);
	}

	@RequestMapping(value = "/validate/home", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<Booking> checkTime(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(name = "classPK", required = false) Long classPK,
			@RequestParam(name = "fromDate", required = false) String fromDate)
			throws UnsupportedEncodingException, IOException, ParseException {

		return BookingBusinessFactoryUtil.checkTime(classPK, Home.class.getName(), fromDate);
	}

	@RequestMapping(value = "/booking/home", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> bookingHome(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @RequestBody Booking booking) throws UnsupportedEncodingException, IOException {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return BookingBusinessFactoryUtil.createBooking(request, response, booking.getNumberOfGuest(),
				booking.getFromDate(), booking.getToDate(), booking.getClassPK(), Home.class.getName(),
				booking.getFullName(), booking.getEmail(), booking.getPhone(), booking.getStateId(),
				booking.getStateName(),
				"Thanh toan don hang thoi gian: " + DateFormat.formatDateToString_ddMMyyyy_HHmmss(new Date()),
				"billpayment", "NCB", "vn", userContext);
	}

	@RequestMapping(value = "/vnpay/confirm/{bookingId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> confirmPayment(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, @PathVariable("bookingId") long bookingId)
			throws UnsupportedEncodingException, IOException {

		return VnPayPaymentBusinessFactoryUtil.confirm(request, bookingId);
	}
}
