/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.StateCategory;
import com.booking.repository.StateCategoryRepository;
import com.booking.repository.elasticsearch.StateCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
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
		try {
			sessionFactory.getCurrentSession().update(stateCategory);
			return stateCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public StateCategory createStateCategory(StateCategory stateCategory) {
		try {
			sessionFactory.getCurrentSession().save(stateCategory);
			return stateCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public StateCategory deleteStateCategory(long stateId) {
		try {
			StateCategory stateCategory = sessionFactory.getCurrentSession().get(StateCategory.class, stateId);
			sessionFactory.getCurrentSession().delete(stateCategory);
			return stateCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
