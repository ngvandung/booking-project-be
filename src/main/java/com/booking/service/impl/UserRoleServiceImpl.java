/**
 * 
 */
package com.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.UserRole;
import com.booking.repository.UserRoleRepository;
import com.booking.service.UserRoleService;

/**
 * @author ddung
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public UserRole updateUserRole(int roleId, long userId) {
		UserRole userRole = userRoleRepository.findByRoleId_UserId(roleId, userId);
		if (userRole == null) {
			userRole = new UserRole();
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			
			userRole = userRoleRepository.createUserRole(userRole);
		}
		return userRole;
	}

	@Override
	public UserRole createUserRole(UserRole userRole) {
		userRoleRepository.createUserRole(userRole);
		return userRole;
	}

	@Override
	public List<UserRole> findByUserId(long userId) {
		return userRoleRepository.findByUserId(userId);
	}

}
