/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.business.UserRoleBusiness;
import com.booking.model.UserRole;
import com.booking.service.UserRoleService;
import com.booking.util.AuthServiceUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@Service
public class UserRoleBusinessImpl implements UserRoleBusiness {

	@Autowired
	private UserRoleService userRoleService;

	@Override
	public UserRole updateUserRole(int roleId, long userId, UserContext userContext) {
		if (userContext != null && userContext.isSignin()
				&& userContext.getPermission().equals(AuthServiceUtil.DefaultRole.ADMIN.toString())) {

			UserRole userRole = userRoleService.updateUserRole(roleId, userId);

			return userRole;
		}
		return null;
	}

}
