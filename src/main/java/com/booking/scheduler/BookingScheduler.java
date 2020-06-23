/**
 * 
 */
package com.booking.scheduler;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.annotation.Transactional;

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
public class BookingScheduler {
	private TaskScheduler scheduler = new ConcurrentTaskScheduler();
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

	public BookingScheduler(TaskScheduler scheduler, BookingService bookingService, HomeService homeService) {
		super();
		this.scheduler = scheduler;
		this.bookingService = bookingService;
		this.homeService = homeService;
	}

	public TaskScheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(TaskScheduler scheduler) {
		this.scheduler = scheduler;
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

	@PostConstruct
	private void executeJob() {
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			@Transactional
			public void run() {
				Date now = new Date();
				_log.info("Booking Scheduler: " + DateFormat.formatDateToString_ddMMyyyy_HHmmss(now));

				List<Booking> bookings = bookingService.findByToDate(now, BookingConstant.RENTING);
				for (Booking booking : bookings) {
					// update status booking
					booking.setBookingStatus("done");
					bookingService.updateBooking(booking);

					long classPK = booking.getClassPK();
					String className = booking.getClassName();
					open(classPK, className);
				}
			}
		}, 30000);
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
