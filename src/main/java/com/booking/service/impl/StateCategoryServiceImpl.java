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

import com.booking.model.StateCategory;
import com.booking.model.User;
import com.booking.repository.StateCategoryRepository;
import com.booking.service.CounterService;
import com.booking.service.StateCategoryService;

/**
 * @author ddung
 *
 */
@Service
public class StateCategoryServiceImpl implements StateCategoryService {
	private static final Logger log = Logger.getLogger(StateCategoryServiceImpl.class);

	@Autowired
	private StateCategoryRepository stateCategoryRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start, Integer end) {
		return stateCategoryRepository.getStateCategories(stateName, isActive, start, end);
	}

	@Override
	public StateCategory updateStateCategory(long stateId, String stateName, Integer isActive, long userId) {
		StateCategory stateCategory = stateCategoryRepository.findById(stateId);

		if (stateCategory != null) {
			stateCategory.setStateName(stateName);
			stateCategory.setModifiedDate(new Date());
			stateCategory.setIsActive(isActive);
			stateCategory.setUserId(userId);

			stateCategory = stateCategoryRepository.updateStateCategory(stateCategory);
			if (stateCategory != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(stateCategory.getStateId()))
						.withObject(stateCategory).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return stateCategory;
	}

	@Override
	public StateCategory createStateCategory(String stateName, long userId) {
		StateCategory stateCategory = new StateCategory();

		long stateId = counterService.increment(StateCategory.class.getName());

		stateCategory.setStateId(stateId);
		stateCategory.setStateName(stateName);
		stateCategory.setIsActive(1);
		stateCategory.setCreateDate(new Date());
		stateCategory.setModifiedDate(new Date());
		stateCategory.setUserId(userId);

		stateCategory = stateCategoryRepository.createStateCategory(stateCategory);
		if (stateCategory != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(stateCategory.getStateId()))
					.withObject(stateCategory).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return stateCategory;
	}

	@Override
	public StateCategory deleteStateCategory(long stateId) {
		StateCategory stateCategory = stateCategoryRepository.deleteStateCategory(stateId);
		if (stateCategory != null) {
			String documentId = elasticsearchOperations.delete(User.class, String.valueOf(stateId));
			log.info("documentId: " + documentId);
		}
		return stateCategory;
	}

	@Override
	public StateCategory findById(long stateId) {
		return stateCategoryRepository.findById(stateId);
	}
}
