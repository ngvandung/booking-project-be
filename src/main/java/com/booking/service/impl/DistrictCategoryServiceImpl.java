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

import com.booking.model.DistrictCategory;
import com.booking.model.User;
import com.booking.repository.DistrictCategoryRepository;
import com.booking.service.CounterService;
import com.booking.service.DistrictCategoryService;

/**
 * @author ddung
 *
 */
@Service
public class DistrictCategoryServiceImpl implements DistrictCategoryService {
	private static final Logger log = Logger.getLogger(DistrictCategoryServiceImpl.class);

	@Autowired
	private DistrictCategoryRepository districtCategoryRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId, Integer start,
			Integer end) {
		return districtCategoryRepository.getDistrictCategories(districtName, isActive, cityId, start, end);
	}

	@Override
	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive, long cityId,
			long userId) {
		DistrictCategory districtCategory = districtCategoryRepository.findById(districtId);

		if (districtCategory != null) {
			districtCategory.setDistrictName(districtName);
			districtCategory.setModifiedDate(new Date());
			districtCategory.setIsActive(isActive);
			districtCategory.setUserId(userId);
			districtCategory.setCityId(cityId);

			districtCategory = districtCategoryRepository.updateDistrictCategory(districtCategory);
			if (districtCategory != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(districtCategory.getDistrictId()))
						.withObject(districtCategory).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return districtCategory;
	}

	@Override
	public DistrictCategory createDistrictCategory(String districtName, long cityId, long userId) {
		DistrictCategory districtCategory = new DistrictCategory();

		long districtId = counterService.increment(DistrictCategory.class.getName());

		districtCategory.setDistrictId(districtId);
		districtCategory.setDistrictName(districtName);
		districtCategory.setIsActive(1);
		districtCategory.setCityId(cityId);
		districtCategory.setCreateDate(new Date());
		districtCategory.setModifiedDate(new Date());
		districtCategory.setUserId(userId);

		districtCategory = districtCategoryRepository.createDistrictCategory(districtCategory);
		if (districtCategory != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(districtCategory.getDistrictId()))
					.withObject(districtCategory).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return districtCategory;
	}

	@Override
	public DistrictCategory deleteDistrictCategory(long districtId) {
		DistrictCategory DistrictCategory = districtCategoryRepository.deleteDistrictCategory(districtId);
		if (DistrictCategory != null) {
			String documentId = elasticsearchOperations.delete(User.class, String.valueOf(districtId));
			log.info("documentId: " + documentId);
		}
		return DistrictCategory;
	}

	@Override
	public DistrictCategory findById(long districtId) {
		return districtCategoryRepository.findById(districtId);
	}
}
