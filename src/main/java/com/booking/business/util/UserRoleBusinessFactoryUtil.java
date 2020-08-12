/**
 * 
 */
package com.booking.business.util;

import com.booking.business.UserRoleBusiness;
import com.booking.model.UserRole;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class UserRoleBusinessFactoryUtil {
	// Design pattern - Singleton
	private static UserRoleBusiness _userRoleBusiness;

	public static UserRoleBusiness getUserRoleBusiness() {

		if (_userRoleBusiness == null) {
			_userRoleBusiness = BeanUtil.getBean(UserRoleBusiness.class);
		}
		return _userRoleBusiness;
	}
	// ============================

	public static UserRole updateUserRole(int roleId, long userId, UserContext userContext) {
		return getUserRoleBusiness().updateUserRole(roleId, userId, userContext);
	}
}
