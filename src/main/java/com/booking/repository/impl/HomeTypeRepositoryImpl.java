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

import com.booking.model.HomeType;
import com.booking.repository.HomeTypeRepository;
import com.booking.repository.elasticsearch.HomeTypeElasticsearchRepository;
import com.booking.util.HibernateUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
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
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				homeType = session.get(HomeType.class, homeTypeId);
				transaction.commit();
				session.close();
			}
			return homeType;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public HomeType updateHomeType(HomeType homeType) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(homeType);
		transaction.commit();
		session.close();
		return homeType;
	}

	@Override
	public HomeType createHomeType(HomeType homeType) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(homeType);
		transaction.commit();
		session.close();
		return homeType;
	}

	@Override
	public HomeType deleteHomeType(long homeTypeId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		HomeType homeType = session.get(HomeType.class, homeTypeId);
		session.delete(homeType);
		transaction.commit();
		session.close();
		return homeType;
	}

	@Override
	public Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByHomeTypeId = PageRequest.of(start, end, Sort.by("homeTypeId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (typeName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("typeName", typeName);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByHomeTypeId).build();

		return homeTypeElasticsearchRepository.search(searchQuery);
	}

	@Override
	public List<HomeType> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<HomeType> homeTypes = HibernateUtil.loadAllData(HomeType.class, session);
		transaction.commit();
		session.close();
		return homeTypes;
	}
}
