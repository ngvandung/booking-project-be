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
import com.booking.constant.HouseConstant;
import com.booking.model.Booking;
import com.booking.model.House;
import com.booking.service.BookingService;
import com.booking.service.HouseService;
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
	private HouseService houseService;

	public BookingScheduler() {
	}

	public BookingScheduler(BookingService bookingService, HouseService houseService) {
		super();
		this.bookingService = bookingService;
		this.houseService = houseService;
	}

	public BookingScheduler(BookingService bookingService) {
		super();
		this.bookingService = bookingService;
	}

	public BookingScheduler(HouseService houseService) {
		super();
		this.houseService = houseService;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public void setBookingService(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	public HouseService getHouseService() {
		return houseService;
	}

	public void setHouseService(HouseService houseService) {
		this.houseService = houseService;
	}

	// @Async
	@Scheduled(fixedRate = 30000)
	public void bookingScheduler() {
		Date now = new Date();
		_log.info("Booking Scheduler: " + DateFormat.formatDateToString_ddMMyyyy_HHmmss(now));

		String condition = "'" + BookingConstant.RENTING + "','" + BookingConstant.CANCEL_FAILED + "','"
				+ BookingConstant.CANCEL_PENDING + "'"; // Hoac neu dang paying thi neu trong 2 tieng khong thanh toan se bi khoa
		List<Booking> bookings = bookingService.findByToDate(condition);
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
		if (className.equals(House.class.getName())) {
			House house = houseService.findById(classPK);
			house.setIsActive(HouseConstant.ACTIVE);
			houseService.updateHouse(house);
		}
	}
}
