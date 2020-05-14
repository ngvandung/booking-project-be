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

import com.booking.model.Location;
import com.booking.repository.LocationRepository;
import com.booking.repository.elasticsearch.LocationElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class LocationRepositoryImpl implements LocationRepository {
	private static final Logger log = Logger.getLogger(LocationRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private LocationElasticsearchRepository locationElasticsearchRepository;

	@Override
	public Location findById(long locationId) {
		Location location = null;
		try {
			Optional<Location> optionalLocation = locationElasticsearchRepository.findById(locationId);
			if (optionalLocation.isPresent()) {
				location = optionalLocation.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				location = session.get(Location.class, locationId);
				transaction.commit();
				session.close();
			}
			return location;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Location updateLocation(Location location) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(location);
		transaction.commit();
		session.close();
		return location;
	}

	@Override
	public Location createLocation(Location location) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(location);
		transaction.commit();
		session.close();
		return location;
	}

	@Override
	public Location deleteLocation(long locationId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Location location = session.get(Location.class, locationId);
		session.delete(location);
		transaction.commit();
		session.close();
		return location;
	}
}
