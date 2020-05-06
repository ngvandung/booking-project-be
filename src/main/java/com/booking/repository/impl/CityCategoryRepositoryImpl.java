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
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.booking.constant.CityCategoryConstant;
import com.booking.model.CityCategory;
import com.booking.repository.CityCategoryRepository;
import com.booking.repository.elasticsearch.CityCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
public class CityCategoryRepositoryImpl implements CityCategoryRepository {

	private static final Logger log = Logger.getLogger(CityCategoryRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CityCategoryElasticsearchRepository cityCategoryElasticsearchRepository;

	@Override
	public CityCategory findById(long cityId) {
		CityCategory cityCategory = null;
		try {
			Optional<CityCategory> optionalCityCategory = cityCategoryElasticsearchRepository.findById(cityId);
			if (optionalCityCategory.isPresent()) {
				cityCategory = optionalCityCategory.get();
			} else {
				cityCategory = sessionFactory.getCurrentSession().get(CityCategory.class, cityId);
			}
			return cityCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public CityCategory updateCityCategory(CityCategory cityCategory) {
		try {
			sessionFactory.getCurrentSession().update(cityCategory);
			return cityCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public CityCategory createCityCategory(CityCategory cityCategory) {
		try {
			sessionFactory.getCurrentSession().save(cityCategory);
			return cityCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public CityCategory deleteCityCategory(long cityId) {
		try {
			CityCategory cityCategory = sessionFactory.getCurrentSession().get(CityCategory.class, cityId);
			sessionFactory.getCurrentSession().delete(cityCategory);
			return cityCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Integer start, Integer end) {
		if (start == null || end == null) {
			start = 0;
			end = 15;
		}
		Pageable sortedByCityId = PageRequest.of(start, end, Sort.by("cityId"));

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.matchAllQuery());
		if (cityName != null) {
			QueryBuilder queryBuilder = QueryBuilders.matchQuery(CityCategoryConstant.CITYNAME, cityName)
					.operator(Operator.AND);
			boolQueryBuilder.must(queryBuilder);
		}
		if (isActive != null) {
			QueryBuilder queryBuilder = QueryBuilders.termQuery(CityCategoryConstant.ISACTIVE, isActive);
			boolQueryBuilder.must(queryBuilder);
		}

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder)
				.withPageable(sortedByCityId).build();

		return cityCategoryElasticsearchRepository.search(searchQuery);
	}

}
