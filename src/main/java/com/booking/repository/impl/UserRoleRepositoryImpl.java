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
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.UserRole;
import com.booking.repository.UserRoleRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class UserRoleRepositoryImpl implements UserRoleRepository {

	private static final Logger log = Logger.getLogger(UserRoleRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<UserRole> findByUserId(long userId) {
		List<UserRole> userRole = new ArrayList<UserRole>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
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
		session.close();
		return userRole;
	}

	@Override
	public UserRole updateUserRole(UserRole userRole) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.update(userRole);
		transaction.commit();
		session.close();
		return userRole;
	}

	@Override
	public UserRole createUserRole(UserRole userRole) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		session.save(userRole);
		transaction.commit();
		session.close();
		return userRole;
	}

	@Override
	public UserRole findByRoleId_UserId(int roleId, long userId) {
		Transaction transaction = null;
		Session session = sessionFactory.openSession();
		UserRole userRole = null;
		try {
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
		session.close();
		return userRole;
	}

	@Override
	public String findUserIdByDiffRoleId(int... roleId) {
		List<UserRole> userRoles = new ArrayList<UserRole>();
		StringBuilder result = new StringBuilder();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				StringBuilder hql = new StringBuilder();
				hql.append("SELECT UR FROM UserRole UR WHERE 1 = 1 ");
				if (roleId.length > 0) {
					StringBuilder condition = new StringBuilder();
					for (int i = 0; i < roleId.length; i++) {
						if (!condition.toString().equals("")) {
							condition.append(" OR");
						}
						condition.append(" UR.roleId = :roleId" + i);
					}
					hql.append(" AND (");
					hql.append(condition);
					hql.append(")");
				}
				Query query = session.createQuery(hql.toString());
				for (int i = 0; i < roleId.length; i++) {
					query.setParameter("roleId" + i, roleId[i]);
				}
				userRoles = (List<UserRole>) query.getResultList();

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
		for (int i = userRoles.size() - 1; i >= 0; i--) {
			if (!result.toString().equals("")) {
				result.append(",");
			}
			result.append(userRoles.get(i).getUserId());
		}
		return result.toString();
	}

}
