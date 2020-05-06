/**
 * 
 */
package com.booking.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * @author ddung
 *
 */
@Entity
@Table(name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole {
	@Id
	private int roleId;
	@Id
	private long userId;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
