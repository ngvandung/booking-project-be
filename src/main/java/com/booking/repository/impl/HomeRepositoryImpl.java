/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Home;
import com.booking.repository.HomeRepository;
import com.booking.repository.elasticsearch.HomeElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
public class HomeRepositoryImpl implements HomeRepository {
	private static final Logger log = Logger.getLogger(HomeRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HomeElasticsearchRepository homeElasticsearchRepository;

	@Override
	public Home findById(long homeId) {
		Home home = null;
		try {
			Optional<Home> optionalHome = homeElasticsearchRepository.findById(homeId);
			if (optionalHome.isPresent()) {
				home = optionalHome.get();
			} else {
				home = sessionFactory.getCurrentSession().get(Home.class, homeId);
			}
			return home;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Home updateHome(Home home) {
		try {
			sessionFactory.getCurrentSession().update(home);
			return home;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Home createHome(Home home) {
		try {
			sessionFactory.getCurrentSession().save(home);
			return home;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Home deleteHome(long homeId) {
		try {
			Home home = sessionFactory.getCurrentSession().get(Home.class, homeId);
			sessionFactory.getCurrentSession().delete(home);
			return home;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
