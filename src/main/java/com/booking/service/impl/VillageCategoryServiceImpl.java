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

import com.booking.model.VillageCategory;
import com.booking.model.User;
import com.booking.repository.VillageCategoryRepository;
import com.booking.service.CounterService;
import com.booking.service.VillageCategoryService;

/**
 * @author ddung
 *
 */
@Service
public class VillageCategoryServiceImpl implements VillageCategoryService {

	private static final Logger log = Logger.getLogger(VillageCategoryServiceImpl.class);

	@Autowired
	private VillageCategoryRepository villageCategoryRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Integer start,
			Integer end) {
		// return villageCategoryRepository.getVillageCategories(villageName, isActive,
		// start, end);
		return null;
	}

	@Override
	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive, long userId) {
		VillageCategory villageCategory = villageCategoryRepository.findById(villageId);

		if (villageCategory != null) {
			villageCategory.setVillageName(villageName);
			villageCategory.setModifiedDate(new Date());
			villageCategory.setIsActive(isActive);
			villageCategory.setUserId(userId);

			villageCategory = villageCategoryRepository.updateVillageCategory(villageCategory);
			if (villageCategory != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(villageCategory.getVillageId()))
						.withObject(villageCategory).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return villageCategory;
	}

	@Override
	public VillageCategory createVillageCategory(String villageName, long userId) {
		VillageCategory villageCategory = new VillageCategory();

		long villageId = counterService.increment(VillageCategory.class.getName());

		villageCategory.setVillageId(villageId);
		villageCategory.setVillageName(villageName);
		villageCategory.setIsActive(1);
		villageCategory.setCreateDate(new Date());
		villageCategory.setModifiedDate(new Date());
		villageCategory.setUserId(userId);

		villageCategory = villageCategoryRepository.createVillageCategory(villageCategory);
		if (villageCategory != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(villageCategory.getVillageId()))
					.withObject(villageCategory).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return villageCategory;
	}

	@Override
	public VillageCategory deleteVillageCategory(long villageId) {
		VillageCategory villageCategory = villageCategoryRepository.deleteVillageCategory(villageId);
		if (villageCategory != null) {
			String documentId = elasticsearchOperations.delete(User.class, String.valueOf(villageId));
			log.info("documentId: " + documentId);
		}
		return villageCategory;
	}

	@Override
	public VillageCategory findById(long villageId) {
		return villageCategoryRepository.findById(villageId);
	}
}
