/**
 * 
 */
package com.booking.business;

import com.booking.model.UserRole;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface UserRoleBusiness {
	public UserRole updateUserRole(int roleId, long userId, UserContext userContext);
}
