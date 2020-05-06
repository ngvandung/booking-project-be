/**
 * 
 */
package com.booking.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Counter;
import com.booking.repository.CounterRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional
public class CounterRepositoryImpl implements CounterRepository {

	private static final Logger log = Logger.getLogger(CounterRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Counter createCounter(Counter counter) {
		try {
			sessionFactory.getCurrentSession().save(counter);
			return counter;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Counter updateCounter(Counter counter) {
		try {
			sessionFactory.getCurrentSession().update(counter);
			return counter;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Counter findById(String clazz) {
		try {
			Counter counter = sessionFactory.getCurrentSession().find(Counter.class, clazz);
			return counter;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

}
