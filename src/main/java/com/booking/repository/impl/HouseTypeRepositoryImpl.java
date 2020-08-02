/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.HouseType;
import com.booking.repository.HouseTypeRepository;
import com.booking.repository.elasticsearch.HouseTypeElasticsearchRepository;
import com.booking.util.HibernateUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class HouseTypeRepositoryImpl implements HouseTypeRepository {
	private static final Logger log = Logger.getLogger(HouseTypeRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HouseTypeElasticsearchRepository houseTypeElasticsearchRepository;

	@Override
	public HouseType findById(long houseTypeId) {
		HouseType houseType = null;
		try {
			Optional<HouseType> optionalHouseType = houseTypeElasticsearchRepository.findById(houseTypeId);
			if (optionalHouseType.isPresent()) {
				houseType = optionalHouseType.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				houseType = session.get(HouseType.class, houseTypeId);
				transaction.commit();
				session.close();
			}
			return houseType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public HouseType updateHouseType(HouseType houseType) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(houseType);
		transaction.commit();
		session.close();
		return houseType;
	}

	@Override
	public HouseType createHouseType(HouseType houseType) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(houseType);
		transaction.commit();
		session.close();
		return houseType;
	}

	@Override
	public HouseType deleteHouseType(long houseTypeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		HouseType houseType = session.get(HouseType.class, houseTypeId);
		session.delete(houseType);
		transaction.commit();
		session.close();
		return houseType;
	}

	@Override
	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByHouseTypeId = PageRequest.of(start, end, Sort.by("houseTypeId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (typeName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("typeName", typeName);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByHouseTypeId).build();

		return houseTypeElasticsearchRepository.search(searchQuery);
	}

	@Override
	public List<HouseType> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<HouseType> houseTypes = HibernateUtil.loadAllData(HouseType.class, session);
		transaction.commit();
		session.close();
		return houseTypes;
	}
}
