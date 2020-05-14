/**
 * 
 */
package com.booking.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Role;
import com.booking.repository.RoleRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Role findByRoleId(int roleId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Role role = session.get(Role.class, roleId);
		transaction.commit();
		session.close();
		return role;
	}

}
