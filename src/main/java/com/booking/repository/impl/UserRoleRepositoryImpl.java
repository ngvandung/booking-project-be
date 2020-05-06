/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.UserRole;
import com.booking.repository.UserRoleRepository;

/**
 * @author ddung
 *
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private static final Logger log = Logger.getLogger(UserRoleRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserRole> findByUserId(long userId) {
		Transaction transaction = null;
		List<UserRole> userRole = new ArrayList<UserRole>();
		try {
			Session session = sessionFactory.getSessionFactory().openSession();
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM UserRole S WHERE S.userId = :userId";
				Query query = session.createQuery(hql);
				query.setParameter("userId", userId);
				userRole = (List<UserRole>) query.getResultList();

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		return userRole;
	}

	@Override
	public UserRole updateUserRole(UserRole userRole) {
		try {
			sessionFactory.getCurrentSession().update(userRole);
			return userRole;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public UserRole createUserRole(UserRole userRole) {
		try {
			sessionFactory.getCurrentSession().save(userRole);
			return userRole;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public UserRole findByRoleId_UserId(int roleId, long userId) {
		Transaction transaction = null;
		UserRole userRole = null;
		try {
			Session session = sessionFactory.getSessionFactory().openSession();
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM UserRole S WHERE S.userId = :userId AND S.roleId = :roleId";
				Query query = session.createQuery(hql);
				query.setParameter("userId", userId);
				query.setParameter("roleId", roleId);
				List<UserRole> userRoles = new ArrayList<UserRole>();
				userRoles = (List<UserRole>) query.getResultList();
				if (userRoles != null && !userRoles.isEmpty()) {
					userRole = userRoles.get(0);
				}

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		return userRole;
	}

}
