/**
 * 
 */
package com.booking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ddung
 *
 */
@Entity
@Table(name = "role")
public class Role {
	@Id
	private Integer roleId;
	@Column(name = "roleName", nullable = false)
	private String roleName;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
