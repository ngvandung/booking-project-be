/**
 * 
 */
package com.booking.repository.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Counter;
import com.booking.repository.CounterRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class CounterRepositoryImpl implements CounterRepository {

	private static final Logger log = Logger.getLogger(CounterRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Counter createCounter(Counter counter) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(counter);
		transaction.commit();
		session.close();
		return counter;
	}

	@Override
	public Counter updateCounter(Counter counter) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(counter);
		transaction.commit();
		session.close();
		return counter;
	}

	@Override
	public Counter findById(String clazz) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Counter counter = session.find(Counter.class, clazz);
		transaction.commit();
		session.close();
		return counter;
	}

}
