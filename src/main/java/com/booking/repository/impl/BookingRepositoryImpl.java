/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.repository.BookingRepository;
import com.booking.repository.elasticsearch.BookingElasticsearchRepository;
import com.booking.util.RentUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class BookingRepositoryImpl implements BookingRepository {
	private static final Logger log = Logger.getLogger(BookingRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private BookingElasticsearchRepository BookingElasticsearchRepository;

	@Override
	public Booking findById(long bookingId) {
		Booking booking = null;
		try {
			Optional<Booking> optionalBooking = BookingElasticsearchRepository.findById(bookingId);
			if (optionalBooking.isPresent()) {
				booking = optionalBooking.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				booking = session.get(Booking.class, bookingId);
				transaction.commit();
				session.close();
			}
			return booking;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Booking updateBooking(Booking booking) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(booking);
		transaction.commit();
		session.close();
		return booking;
	}

	@Override
	public Booking createBooking(Booking booking) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(booking);
		transaction.commit();
		session.close();
		return booking;
	}

	@Override
	public Booking deleteBooking(long bookingId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Booking booking = session.get(Booking.class, bookingId);
		session.delete(booking);
		transaction.commit();
		session.close();
		return booking;
	}

	@Override
	public List<Booking> findByToDate(Date now, String bookingStatus) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Booking> bookings = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				String hql = " FROM Booking U WHERE U.toDate < :now AND U.bookingStatus = :bookingStatus";
				Query query = session.createQuery(hql);
				query.setParameter("now", now);
				query.setParameter("bookingStatus", bookingStatus);
				bookings = query.getResultList();

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return bookings;
	}

	@Override
	public List<Booking> findBookings(String className, Long classPK, Double totalAmount, Integer numberOfGuest,
			String bookingStatus, Long userId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Booking> bookings = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				String hql = " FROM Booking U WHERE 1 = 1";
				StringBuilder condition = new StringBuilder();
				if (className != null) {
					condition.append(" AND U.className = :className");
				}
				if (classPK != null) {
					condition.append(" AND U.classPK = :classPK");
				}
				if (totalAmount != null) {
					condition.append(" AND U.totalAmount = :totalAmount");
				}
				if (numberOfGuest != null) {
					condition.append(" AND U.numberOfGuest = :numberOfGuest");
				}
				if (bookingStatus != null) {
					condition.append(" AND U.bookingStatus = :bookingStatus");
				}
				if (userId != null) {
					condition.append(" AND U.userId = :userId");
				}
				hql += condition.toString();
				Query query = session.createQuery(hql);
				if (className != null) {
					query.setParameter("className", className);
				}
				if (classPK != null) {
					query.setParameter("classPK", classPK);
				}
				if (totalAmount != null) {
					query.setParameter("totalAmount", totalAmount);
				}
				if (numberOfGuest != null) {
					query.setParameter("numberOfGuest", numberOfGuest);
				}
				if (bookingStatus != null) {
					query.setParameter("bookingStatus", bookingStatus);
				}
				if (userId != null) {
					query.setParameter("userId", userId);
				}
				bookings = query.getResultList();

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return bookings;
	}

	@Override
	public List<Booking> checkTime(Long classPK, String className, Date fromDate, Date toDate, String bookingStatus) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Booking> bookings = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				String hql = " FROM Booking U WHERE 1 = 1";
				StringBuilder condition = new StringBuilder();
				if (className != null) {
					condition.append(" AND U.className = :className");
				}
				if (classPK != null) {
					condition.append(" AND U.classPK = :classPK");
				}
				if (bookingStatus != null) {
					condition.append(" AND U.bookingStatus = :bookingStatus");
				}
				if (fromDate != null) {
					condition.append(
							" AND ((U.fromDate < :fromDate AND U.toDate > :fromDate) OR (U.fromDate < :toDate AND U.toDate > :toDate))");
				}
				hql += condition.toString();
				Query query = session.createQuery(hql);
				if (className != null) {
					query.setParameter("className", className);
				}
				if (classPK != null) {
					query.setParameter("classPK", classPK);
				}
				if (fromDate != null) {
					query.setParameter("fromDate", fromDate);
				}
				if (toDate != null) {
					query.setParameter("toDate", toDate);
				}
				if (bookingStatus != null) {
					query.setParameter("bookingStatus", bookingStatus);
				}
				bookings = query.getResultList();

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return bookings;
	}

	@Override
	public List<Map<String, Object>> findMyBookings(long userId, String className, String bookingStatus) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				String hql = " FROM Booking U INNER JOIN";
				if (className.equals(Home.class.getName())) {
					hql += " Home H ON U.classPK = H.homeId";
				}
				hql += " WHERE 1 = 1 AND U.userId = :userId AND U.className = :className";
				if (bookingStatus != null) {
					hql += " AND U.bookingStatus = :bookingStatus";
				}
				Query query = session.createQuery(hql);
				query.setParameter("userId", userId);
				query.setParameter("className", className);
				if (bookingStatus != null) {
					query.setParameter("bookingStatus", bookingStatus);
				}
				result = RentUtil.convertObjectToMap((List<Object[]>) query.getResultList());

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return result;
	}

	@Override
	public List<Map<String, Object>> findDetailBookings(long ownerId, Long classPK, String className,
			String bookingStatus) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				StringBuilder hql = new StringBuilder();
				if (className.equals(Home.class.getName())) {
					hql.append(
							"FROM Home H INNER JOIN Booking B ON H.homeId = B.classPK AND B.className = :className WHERE H.ownerHomeId = :ownerHomeId");
				}

				if (bookingStatus != null) {
					hql.append(" AND B.bookingStatus = :bookingStatus");
				}
				if (classPK != null) {
					hql.append(" AND B.classPK = :classPK");
				}

				Query query = session.createQuery(hql.toString());

				if (bookingStatus != null) {
					query.setParameter("bookingStatus", bookingStatus);
				}
				if (classPK != null) {
					query.setParameter("classPK", classPK);
				}
				query.setParameter("className", className);
				if (className.equals(Home.class.getName())) {
					query.setParameter("ownerHomeId", ownerId);
				}

				result = RentUtil.convertObjectToMap((List<Object[]>) query.getResultList());

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();

		return result;
	}
}
