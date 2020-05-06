/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.RentHome;
import com.booking.repository.RentHomeRepository;
import com.booking.repository.elasticsearch.RentHomeElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
public class RentHomeRepositoryImpl implements RentHomeRepository{
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
				rentHome = sessionFactory.getCurrentSession().get(RentHome.class, rentId);
			}
			return rentHome;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public RentHome updateRentHome(RentHome rentHome) {
		try {
			sessionFactory.getCurrentSession().update(rentHome);
			return rentHome;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public RentHome createRentHome(RentHome rentHome) {
		try {
			sessionFactory.getCurrentSession().save(rentHome);
			return rentHome;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public RentHome deleteRentHome(long rentId) {
		try {
			RentHome rentHome = sessionFactory.getCurrentSession().get(RentHome.class, rentId);
			sessionFactory.getCurrentSession().delete(rentHome);
			return rentHome;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
