/**
 * 
 */
package com.booking.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.ExtensionCategory;
import com.booking.repository.ExtensionCategoryRepository;
import com.booking.util.HibernateUtil;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class ExtensionCategoryRepositoryImpl implements ExtensionCategoryRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<ExtensionCategory> findAll() {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		List<ExtensionCategory> extensionCategories = HibernateUtil.loadAllData(ExtensionCategory.class, session);
		transaction.commit();
		session.close();
		return extensionCategories;
	}

}
