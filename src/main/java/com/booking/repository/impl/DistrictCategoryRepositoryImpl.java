/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.DistrictCategory;
import com.booking.repository.DistrictCategoryRepository;
import com.booking.repository.elasticsearch.DistrictCategoryElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
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
				districtCategory = sessionFactory.getCurrentSession().get(DistrictCategory.class, districtCategoryId);
			}
			return districtCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public DistrictCategory updateDistrictCategory(DistrictCategory districtCategory) {
		try {
			sessionFactory.getCurrentSession().update(districtCategory);
			return districtCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public DistrictCategory createDistrictCategory(DistrictCategory districtCategory) {
		try {
			sessionFactory.getCurrentSession().save(districtCategory);
			return districtCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public DistrictCategory deleteDistrictCategory(long districtCategoryId) {
		try {
			DistrictCategory districtCategory = sessionFactory.getCurrentSession().get(DistrictCategory.class,
					districtCategoryId);
			sessionFactory.getCurrentSession().delete(districtCategory);
			return districtCategory;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
