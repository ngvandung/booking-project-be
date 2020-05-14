/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.RentHome;
import com.booking.repository.RentHomeRepository;
import com.booking.repository.elasticsearch.RentHomeElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class RentHomeRepositoryImpl implements RentHomeRepository {
	private static final Logger log = Logger.getLogger(RentHomeRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private RentHomeElasticsearchRepository rentHomeElasticsearchRepository;

	@Override
	public RentHome findById(long rentId) {
		RentHome rentHome = null;
		try {
			Optional<RentHome> optionalRentHome = rentHomeElasticsearchRepository.findById(rentId);
			if (optionalRentHome.isPresent()) {
				rentHome = optionalRentHome.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				rentHome = session.get(RentHome.class, rentId);
				transaction.commit();
				session.close();
			}
			return rentHome;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public RentHome updateRentHome(RentHome rentHome) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(rentHome);
		transaction.commit();
		session.close();
		return rentHome;
	}

	@Override
	public RentHome createRentHome(RentHome rentHome) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(rentHome);
		transaction.commit();
		session.close();
		return rentHome;
	}

	@Override
	public RentHome deleteRentHome(long rentId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		RentHome rentHome = session.get(RentHome.class, rentId);
		session.delete(rentHome);
		transaction.commit();
		session.close();
		return rentHome;
	}
}
