/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.UserRole;

/**
 * @author ddung
 *
 */
public interface UserRoleRepository {
	public List<UserRole> findByUserId(long userId);

	public UserRole updateUserRole(UserRole userRole);

	public UserRole createUserRole(UserRole userRole);
	
	public UserRole findByRoleId_UserId(int roleId, long userId);
}
