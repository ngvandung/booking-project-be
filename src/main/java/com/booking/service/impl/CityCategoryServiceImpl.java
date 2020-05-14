/**
 * 
 */

package com.booking.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.model.CityCategory;
import com.booking.model.User;
import com.booking.repository.CityCategoryRepository;
import com.booking.service.CityCategoryService;
import com.booking.service.CounterService;

/**
 * @author ddung
 *
 */
@Service
public class CityCategoryServiceImpl implements CityCategoryService {

	private static final Logger log = Logger.getLogger(CityCategoryServiceImpl.class);

	@Autowired
	private CityCategoryRepository cityCategoryRepository;

	@Resource
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private CounterService counterService;

	@Override
	public Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Long stateId, Integer start, Integer end) {
		return cityCategoryRepository.getCityCategories(cityName, isActive, stateId, start, end);
	}

	@Override
	public CityCategory updateCityCategory(long cityId, String cityName, Integer isActive, long stateId, long userId) {
		CityCategory cityCategory = cityCategoryRepository.findById(cityId);

		if (cityCategory != null) {
			cityCategory.setCityName(cityName);
			cityCategory.setModifiedDate(new Date());
			cityCategory.setIsActive(isActive);
			cityCategory.setStateId(stateId);
			cityCategory.setUserId(userId);

			cityCategory = cityCategoryRepository.updateCityCategory(cityCategory);
			if (cityCategory != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(cityCategory.getCityId()))
						.withObject(cityCategory).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return cityCategory;
	}

	@Override
	public CityCategory createCityCategory(String cityName, long stateId, long userId) {
		CityCategory cityCategory = new CityCategory();

		long cityId = counterService.increment(CityCategory.class.getName());

		cityCategory.setCityId(cityId);
		cityCategory.setCityName(cityName);
		cityCategory.setIsActive(1);
		cityCategory.setStateId(stateId);
		cityCategory.setCreateDate(new Date());
		cityCategory.setModifiedDate(new Date());
		cityCategory.setUserId(userId);

		cityCategory = cityCategoryRepository.createCityCategory(cityCategory);
		if (cityCategory != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(cityCategory.getCityId()))
					.withObject(cityCategory).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return cityCategory;
	}

	@Override
	public CityCategory deleteCityCategory(long cityId) {
		CityCategory cityCategory = cityCategoryRepository.deleteCityCategory(cityId);
		if (cityCategory != null) {
			String documentId = elasticsearchOperations.delete(User.class, String.valueOf(cityId));
			log.info("documentId: " + documentId);
		}
		return cityCategory;
	}

	@Override
	public CityCategory findById(long cityId) {
		return cityCategoryRepository.findById(cityId);
	}

}
