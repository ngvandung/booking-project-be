/**
 * 
 */
package com.booking.repository;

import java.util.Date;
import java.util.List;

import com.booking.model.Booking;

/**
 * @author ddung
 *
 */
public interface BookingRepository {
	public Booking findById(long bookingId);

	public Booking updateBooking(Booking booking);

	public Booking createBooking(Booking booking);

	public Booking deleteBooking(long bookingId);

	public List<Booking> findByToDate(Date now, String bookingStatus);

	public List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId);

	public List<Booking> checkTime(Long classPK, String className, Date fromDate, String bookingStatus);
}
