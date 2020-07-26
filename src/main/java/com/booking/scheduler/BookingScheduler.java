/**
 * 
 */
package com.booking.scheduler;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.booking.constant.BookingConstant;
import com.booking.constant.HomeConstant;
import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.service.BookingService;
import com.booking.service.HomeService;
import com.booking.util.DateFormat;

/**
 * @author ddung
 *
 */
@Configuration
//@EnableAsync
@EnableScheduling
public class BookingScheduler {
	private static final Logger _log = Logger.getLogger(BookingScheduler.class);

	@Autowired
	private BookingService bookingService;
	@Autowired
	private HomeService homeService;

	public BookingScheduler() {
	}

	public BookingScheduler(BookingService bookingService, HomeService homeService) {
		super();
		this.bookingService = bookingService;
		this.homeService = homeService;
	}

	public BookingScheduler(BookingService bookingService) {
		super();
		this.bookingService = bookingService;
	}

	public BookingScheduler(HomeService homeService) {
		super();
		this.homeService = homeService;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public HomeService getHomeService() {
		return homeService;
	}

	public void setHomeService(HomeService homeService) {
		this.homeService = homeService;
	}

	// @Async
	@Scheduled(fixedRate = 30000)
	public void bookingScheduler() {
		Date now = new Date();
		_log.info("Booking Scheduler: " + DateFormat.formatDateToString_ddMMyyyy_HHmmss(now));

		String condition = "'" + BookingConstant.RENTING + "','" + BookingConstant.CANCEL_FAILED + "','"
				+ BookingConstant.CANCEL_PENDING + "'";
		List<Booking> bookings = bookingService.findByToDate(now, condition);
		bookings.parallelStream().forEach(booking -> {
			// update status booking
			booking.setBookingStatus("done");
			bookingService.updateBooking(booking);

			long classPK = booking.getClassPK();
			String className = booking.getClassName();
			open(classPK, className);
		});
	}

	// update status product
	private void open(long classPK, String className) {
		if (className.equals(Home.class.getName())) {
			Home home = homeService.findById(classPK);
			home.setIsActive(HomeConstant.ACTIVE);
			homeService.updateHome(home);
		}
	}
}
