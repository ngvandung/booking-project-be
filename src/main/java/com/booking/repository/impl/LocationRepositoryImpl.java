/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Location;
import com.booking.repository.LocationRepository;
import com.booking.repository.elasticsearch.LocationElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
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
				location = sessionFactory.getCurrentSession().get(Location.class, locationId);
			}
			return location;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Location updateLocation(Location location) {
		try {
			sessionFactory.getCurrentSession().update(location);
			return location;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Location createLocation(Location location) {
		try {
			sessionFactory.getCurrentSession().save(location);
			return location;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Location deleteLocation(long locationId) {
		try {
			Location location = sessionFactory.getCurrentSession().get(Location.class, locationId);
			sessionFactory.getCurrentSession().delete(location);
			return location;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
