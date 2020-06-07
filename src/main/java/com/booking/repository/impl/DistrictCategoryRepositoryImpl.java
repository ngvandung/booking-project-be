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

import com.booking.model.DistrictCategory;
import com.booking.repository.DistrictCategoryRepository;
import com.booking.repository.elasticsearch.DistrictCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class DistrictCategoryRepositoryImpl implements DistrictCategoryRepository {
	private static final Logger log = Logger.getLogger(DistrictCategoryRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private DistrictCategoryElasticsearchRepository districtCategoryElasticsearchRepository;

	@Override
	public DistrictCategory findById(long districtCategoryId) {
		DistrictCategory districtCategory = null;
		try {
			Optional<DistrictCategory> optionalDistrictCategory = districtCategoryElasticsearchRepository
					.findById(districtCategoryId);
			if (optionalDistrictCategory.isPresent()) {
				districtCategory = optionalDistrictCategory.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				districtCategory = session.get(DistrictCategory.class, districtCategoryId);
				transaction.commit();
				session.close();
			}
			return districtCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public DistrictCategory updateDistrictCategory(DistrictCategory districtCategory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(districtCategory);
		transaction.commit();
		session.close();
		return districtCategory;
	}

	@Override
	public DistrictCategory createDistrictCategory(DistrictCategory districtCategory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(districtCategory);
		transaction.commit();
		session.close();
		return districtCategory;
	}

	@Override
	public DistrictCategory deleteDistrictCategory(long districtCategoryId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		DistrictCategory districtCategory = session.get(DistrictCategory.class, districtCategoryId);
		session.delete(districtCategory);
		transaction.commit();
		session.close();
		return districtCategory;
	}

	@Override
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId,
			Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByDistrictId = PageRequest.of(start, end, Sort.by("districtId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (districtName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("districtName", districtName).operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isActive != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("isActive", isActive);
			boolQueryBuilder.must(queryBuilder);
		}
		if (cityId != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("cityId", cityId);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByDistrictId).build();

		return districtCategoryElasticsearchRepository.search(searchQuery);
	}
}
