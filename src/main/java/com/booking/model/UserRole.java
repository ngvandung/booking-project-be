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
	private Integer roleId;
	@Id
	private Long userId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
