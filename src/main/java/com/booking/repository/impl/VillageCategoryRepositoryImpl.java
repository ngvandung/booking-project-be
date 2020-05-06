/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.VillageCategory;
import com.booking.repository.VillageCategoryRepository;
import com.booking.repository.elasticsearch.VillageCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
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
		try {
			sessionFactory.getCurrentSession().save(villageCategory);
			return villageCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
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
}
