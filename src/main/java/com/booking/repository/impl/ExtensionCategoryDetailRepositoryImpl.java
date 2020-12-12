/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.ExtensionCategoryDetail;
import com.booking.repository.ExtensionCategoryDetailRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class ExtensionCategoryDetailRepositoryImpl implements ExtensionCategoryDetailRepository {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger log = Logger.getLogger(ExtensionCategoryDetailRepositoryImpl.class);

	@Override
	public List<ExtensionCategoryDetail> findExtensionCategoryDetails(Long extensionCategoryId,
			String extensionCategoryDetailIds, Long isActive) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		List<ExtensionCategoryDetail> extensionCategoryDetails = null;
		try {
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				StringBuilder hql = new StringBuilder();
				hql.append("FROM ExtensionCategoryDetail E WHERE 1 = 1 ");
				if (extensionCategoryId != null) {
					hql.append(" AND E.extensionCategoryId = :extensionCategoryId");
				}
				if (extensionCategoryDetailIds != null) {
					hql.append(" AND E.extensionCategoryDetailId IN (" + extensionCategoryDetailIds + ")");
				}
				Query query = session.createQuery(hql.toString());
				if (extensionCategoryId != null) {
					query.setParameter("extensionCategoryId", extensionCategoryId);
				}
				extensionCategoryDetails = query.getResultList();

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return extensionCategoryDetails;
	}

}
