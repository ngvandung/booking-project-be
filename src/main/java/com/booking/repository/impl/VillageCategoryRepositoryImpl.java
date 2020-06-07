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

import com.booking.model.VillageCategory;
import com.booking.repository.VillageCategoryRepository;
import com.booking.repository.elasticsearch.VillageCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class VillageCategoryRepositoryImpl implements VillageCategoryRepository {
	private static final Logger log = Logger.getLogger(VillageCategoryRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private VillageCategoryElasticsearchRepository villageCategoryElasticsearchRepository;

	@Override
	public VillageCategory findById(long villageCategoryId) {
		VillageCategory villageCategory = null;
		try {
			Optional<VillageCategory> optionalVillageCategory = villageCategoryElasticsearchRepository
					.findById(villageCategoryId);
			if (optionalVillageCategory.isPresent()) {
				villageCategory = optionalVillageCategory.get();
			} else {
				villageCategory = sessionFactory.getCurrentSession().get(VillageCategory.class, villageCategoryId);
			}
			return villageCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public VillageCategory updateVillageCategory(VillageCategory villageCategory) {
		try {
			sessionFactory.getCurrentSession().update(villageCategory);
			return villageCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public VillageCategory createVillageCategory(VillageCategory villageCategory) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(villageCategory);
		transaction.commit();
		session.close();
		return villageCategory;
	}

	@Override
	public VillageCategory deleteVillageCategory(long villageCategoryId) {
		try {
			VillageCategory villageCategory = sessionFactory.getCurrentSession().get(VillageCategory.class,
					villageCategoryId);
			sessionFactory.getCurrentSession().delete(villageCategory);
			return villageCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Long districtId, Integer start,
			Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByVillageId = PageRequest.of(start, end, Sort.by("villageId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (villageName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery("villageName", villageName).operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isActive != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("isActive", isActive);
			boolQueryBuilder.must(queryBuilder);
		}
		if (districtId != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery("districtId", districtId);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByVillageId).build();

		return villageCategoryElasticsearchRepository.search(searchQuery);
	}
}
