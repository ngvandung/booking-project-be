/**
 * 
 */
package com.booking.repository;

import com.booking.model.Role;

/**
 * @author ddung
 *
 */
public interface RoleRepository {
	public Role findByRoleId(int roleId);
}
