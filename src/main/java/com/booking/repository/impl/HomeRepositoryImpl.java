/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Home;
import com.booking.repository.HomeRepository;
import com.booking.repository.elasticsearch.HomeElasticsearchRepository;
import com.booking.util.HibernateUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
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
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				home = session.get(Home.class, homeId);
				transaction.commit();
				session.close();
			}
			return home;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Home updateHome(Home home) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(home);
		transaction.commit();
		session.close();
		return home;

	}

	@Override
	public Home createHome(Home home) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(home);
		transaction.commit();
		session.close();
		return home;
	}

	@Override
	public Home deleteHome(long homeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Home home = session.get(Home.class, homeId);
		session.delete(home);
		transaction.commit();
		session.close();
		return home;
	}

	@Override
	public List<Home> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<Home> homes = HibernateUtil.loadAllData(Home.class, session);
		transaction.commit();
		session.close();
		return homes;
	}

//	@Override
//	public List<Home> findHomes() {
//		List<Home> homes = new ArrayList<Home>();
//		Session session = sessionFactory.openSession();
//		Transaction transaction = null;
//		try {
//			if (session != null) {
//				// start a transaction
//				transaction = session.beginTransaction();
//
//				// get an student object
//				String hql = " FROM Home H LEFT JOIN Booking WHERE S.className = :className AND S.classPK = :classPK ";
//				Query query = session.createQuery(hql);
//				query.setParameter("className", className);
//				query.setParameter("classPK", classPK);
//				homes = (List<Comment>) query.getResultList();
//
//				// commit transaction
//				transaction.commit();
//			}
//		} catch (Exception e) {
//			if (transaction != null) {
//				transaction.rollback();
//			}
//			log.error(e);
//		}
//		session.close();
//		return comments;
//	}
}
