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

import com.booking.model.House;
import com.booking.repository.HouseRepository;
import com.booking.repository.elasticsearch.HouseElasticsearchRepository;
import com.booking.util.HibernateUtil;
import com.booking.util.RentUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class HouseRepositoryImpl implements HouseRepository {
	private static final Logger log = Logger.getLogger(HouseRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HouseElasticsearchRepository houseElasticsearchRepository;

	@Override
	public House findById(long houseId) {
		House house = null;
		try {
			Optional<House> optionalHouse = houseElasticsearchRepository.findById(houseId);
			if (optionalHouse.isPresent()) {
				house = optionalHouse.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				house = session.get(House.class, houseId);
				transaction.commit();
				session.close();
			}
			return house;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public House updateHouse(House house) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(house);
		transaction.commit();
		session.close();
		return house;

	}

	@Override
	public House createHouse(House house) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(house);
		transaction.commit();
		session.close();
		return house;
	}

	@Override
	public House deleteHouse(long houseId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		House house = session.get(House.class, houseId);
		session.delete(house);
		transaction.commit();
		session.close();
		return house;
	}

	@Override
	public List<House> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<House> houses = HibernateUtil.loadAllData(House.class, session);
		transaction.commit();
		session.close();
		return houses;
	}

	@Override
	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				StringBuilder hql = new StringBuilder();
				hql.append("SELECT DISTINCT H, B.classPK FROM House H LEFT JOIN Booking B ON H.houseId = B.classPK WHERE 1 = 1");
				if (ownerHouseId != null) {
					hql.append(" AND H.ownerHouseId = :ownerHouseId");
				}
				if(flag != null) {
					if(flag.equals("yes")) {
						hql.append(" AND B.classPK IS NOT NULL");
					}
				}
				//hql.append(" GROUP BY H.homeId")

				Query query = session.createQuery(hql.toString());

				if (ownerHouseId != null) {
					query.setParameter("ownerHouseId", ownerHouseId);
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
