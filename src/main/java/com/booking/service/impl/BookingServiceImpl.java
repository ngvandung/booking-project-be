/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.model.Booking;
import com.booking.repository.BookingRepository;
import com.booking.service.CounterService;
import com.booking.service.BookingService;

/**
 * @author ddung
 *
 */
@Service
public class BookingServiceImpl implements BookingService {

	private static final Logger log = Logger.getLogger(BookingServiceImpl.class);

	@Autowired
	private BookingRepository bookingRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Booking findById(long bookingId) {
		return bookingRepository.findById(bookingId);
	}

	@Override
	public Booking updateBooking(long bookingId, int numberOfGuest, Date fromDate, Date toDate, long classPK,
			String className, double totalAmount, String bookingStatus, String fullName, String email, String phone,
			long stateId, String stateName, String ipAddress, long userId) {
		Booking booking = bookingRepository.findById(bookingId);

		if (booking != null) {
			booking.setNumberOfGuest(numberOfGuest);
			booking.setToDate(toDate);
			booking.setFromDate(fromDate);
			booking.setUserId(userId);
			booking.setTotalAmount(totalAmount);
			booking.setModifiedDate(new Date());
			booking.setClassName(className);
			booking.setClassPK(classPK);
			booking.setBookingStatus(bookingStatus);
			booking.setFullName(fullName);
			booking.setEmail(email);
			booking.setPhone(phone);
			booking.setStateId(stateId);
			booking.setStateName(stateName);
			booking.setIpAddress(ipAddress);

			booking = bookingRepository.updateBooking(booking);
			if (booking != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(booking.getBookingId()))
						.withObject(booking).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}
		return booking;
	}

	@Override
	public Booking createBooking(int numberOfGuest, Date fromDate, Date toDate, long classPK, String className,
			double totalAmount, String bookingStatus, String fullName, String email, String phone, long stateId,
			String stateName, String ipAddress, long userId) {
		Booking booking = new Booking();

		long bookingId = counterService.increment(Booking.class.getName());
		booking.setBookingId(bookingId);
		booking.setNumberOfGuest(numberOfGuest);
		booking.setToDate(toDate);
		booking.setFromDate(fromDate);
		booking.setUserId(userId);
		booking.setTotalAmount(totalAmount);
		booking.setModifiedDate(new Date());
		booking.setClassName(className);
		booking.setClassPK(classPK);
		booking.setCreateDate(new Date());
		booking.setBookingStatus(bookingStatus);
		booking.setFullName(fullName);
		booking.setEmail(email);
		booking.setPhone(phone);
		booking.setStateId(stateId);
		booking.setStateName(stateName);
		booking.setIpAddress(ipAddress);

		booking = bookingRepository.createBooking(booking);
		if (booking != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(booking.getBookingId()))
					.withObject(booking).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return booking;

	}

	@Override
	public Booking deleteBooking(long bookingId) {
		Booking booking = bookingRepository.deleteBooking(bookingId);
		if (booking != null) {
			String documentId = elasticsearchOperations.delete(Booking.class, String.valueOf(bookingId));
			log.info("documentId: " + documentId);
		}
		return booking;
	}

	@Override
	public Booking updateBooking(Booking booking) {
		booking = bookingRepository.updateBooking(booking);
		if (booking != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(booking.getBookingId()))
					.withObject(booking).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return booking;
	}

	@Override
	public List<Booking> findByToDate(Date now, String bookingStatus) {
		return bookingRepository.findByToDate(now, bookingStatus);
	}

	@Override
	public List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId) {
		return bookingRepository.findBookings(className, classPK, totalAmount, numberOfGuest, bookingStatus, userId);
	}

	@Override
	public List<Booking> checkTime(Long classPK, String className, Date fromDate, Date toDate, String bookingStatus) {
		return bookingRepository.checkTime(classPK, className, fromDate, toDate, bookingStatus);
	}

	@Override
	public List<Map<String, Object>> findMyBookings(long userId, String className, String bookingStatus) {
		return bookingRepository.findMyBookings(userId, className, bookingStatus);
	}

	@Override
	public List<Map<String, Object>> findDetailBookings(long ownerId, Long classPK, String className,
			String bookingStatus) {
		return bookingRepository.findDetailBookings(ownerId, classPK, className, bookingStatus);
	}

}
