/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
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

import com.booking.model.Home;
import com.booking.repository.HomeRepository;
import com.booking.repository.elasticsearch.HomeElasticsearchRepository;
import com.booking.util.HibernateUtil;
import com.booking.util.RentUtil;

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

	@Override
	public List<Map<String, Object>> findMyHomes(Long ownerHomeId, String flag) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				StringBuilder hql = new StringBuilder();
				hql.append("SELECT DISTINCT H, B.classPK FROM Home H LEFT JOIN Booking B ON H.homeId = B.classPK WHERE 1 = 1");
				if (ownerHomeId != null) {
					hql.append(" AND H.ownerHomeId = :ownerHomeId");
				}
				if(flag != null) {
					if(flag.equals("yes")) {
						hql.append(" AND B.classPK IS NOT NULL");
					}
				}
				//hql.append(" GROUP BY H.homeId")

				Query query = session.createQuery(hql.toString());

				if (ownerHomeId != null) {
					query.setParameter("ownerHomeId", ownerHomeId);
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
