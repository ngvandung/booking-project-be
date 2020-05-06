/**
 * 
 */
package com.booking.repository.impl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Role;
import com.booking.repository.RoleRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional
public class RoleRepositoryImpl implements RoleRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public Role findByRoleId(int roleId) {
		return sessionFactory.getCurrentSession().get(Role.class, roleId);
	}

}
