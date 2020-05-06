/**
 * 
 */
package com.booking.service.impl;

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
		if (userRole != null) {
			userRole.setRoleId(roleId);

			userRole = userRoleRepository.updateUserRole(userRole);
		}
		return userRole;
	}

}
