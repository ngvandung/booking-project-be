/**
 * 
 */
package com.booking.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.booking.model.Booking;

/**
 * @author ddung
 *
 */
public interface BookingService {
	public Booking findById(long bookingId);

	public Booking updateBooking(long bookingId, int numberOfGuest, Date fromDate, Date toDate, long classPK,
			String className, double totalAmount, String bookingStatus, String fullName, String email, String phone,
			long stateId, String stateName, String ipAddress, long userId);

	public Booking createBooking(int numberOfGuest, Date fromDate, Date toDate, long classPK, String className,
			double totalAmount, String bookingStatus, String fullName, String email, String phone, long stateId,
			String stateName, String ipAddress, long userId);

	public Booking deleteBooking(long bookingId);

	public Booking updateBooking(Booking booking);

	public List<Booking> findByToDate(Date now, String bookingStatus);

	public List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId);

	public List<Booking> checkTime(Long classPK, String className, Date fromDate, Date toDate, String bookingStatus);

	public List<Map<String, Object>> findMyBookings(long userId, String className, String bookingStatus);

	public List<Map<String, Object>> findDetailBookings(long ownerId, Long classPK, String className,
			String bookingStatus);

}
