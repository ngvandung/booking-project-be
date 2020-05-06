/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.HomeType;
import com.booking.repository.HomeTypeRepository;
import com.booking.repository.elasticsearch.HomeTypeElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
public class HomeTypeRepositoryImpl implements HomeTypeRepository {
	private static final Logger log = Logger.getLogger(HomeTypeRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HomeTypeElasticsearchRepository homeTypeElasticsearchRepository;

	@Override
	public HomeType findById(long homeTypeId) {
		HomeType homeType = null;
		try {
			Optional<HomeType> optionalHomeType = homeTypeElasticsearchRepository.findById(homeTypeId);
			if (optionalHomeType.isPresent()) {
				homeType = optionalHomeType.get();
			} else {
				homeType = sessionFactory.getCurrentSession().get(HomeType.class, homeTypeId);
			}
			return homeType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public HomeType updateHomeType(HomeType homeType) {
		try {
			sessionFactory.getCurrentSession().update(homeType);
			return homeType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public HomeType createHomeType(HomeType homeType) {
		try {
			sessionFactory.getCurrentSession().save(homeType);
			return homeType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public HomeType deleteHomeType(long homeTypeId) {
		try {
			HomeType homeType = sessionFactory.getCurrentSession().get(HomeType.class, homeTypeId);
			sessionFactory.getCurrentSession().delete(homeType);
			return homeType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
