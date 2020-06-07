/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.UserRole;

/**
 * @author ddung
 *
 */
public interface UserRoleService {
	public UserRole updateUserRole(int roleId, long userId);
	
	public UserRole createUserRole(UserRole userRole);
	
	public List<UserRole> findByUserId(long userId);
}
