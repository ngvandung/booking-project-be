/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
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

import com.booking.model.StateCategory;
import com.booking.repository.StateCategoryRepository;
import com.booking.repository.elasticsearch.StateCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class StateCategoryRepositoryImpl implements StateCategoryRepository {
	private static final Logger log = Logger.getLogger(StateCategoryRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private StateCategoryElasticsearchRepository stateCategoryElasticsearchRepository;

	@Override
	public StateCategory findById(long stateId) {
		StateCategory stateCategory = null;
		try {
			Optional<StateCategory> optionalStateCategory = stateCategoryElasticsearchRepository.findById(stateId);
			if (optionalStateCategory.isPresent()) {
				stateCategory = optionalStateCategory.get();
			} else {
				stateCategory = sessionFactory.getCurrentSession().get(StateCategory.class, stateId);
			}
			return stateCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public StateCategory updateStateCategory(StateCategory stateCategory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(stateCategory);
		transaction.commit();
		session.close();
		return stateCategory;
	}

	@Override
	public StateCategory createStateCategory(StateCategory stateCategory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(stateCategory);
		transaction.commit();
		session.close();
		return stateCategory;
	}

	@Override
	public StateCategory deleteStateCategory(long stateId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		StateCategory stateCategory = session.get(StateCategory.class, stateId);
		session.delete(stateCategory);
		transaction.commit();
		session.close();
		return stateCategory;
	}

	@Override
	public Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByStateId = PageRequest.of(start, end, Sort.by("stateId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (stateName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("stateName", stateName).operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isActive != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("isActive", isActive);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByStateId).build();

		return stateCategoryElasticsearchRepository.search(searchQuery);
	}
}
