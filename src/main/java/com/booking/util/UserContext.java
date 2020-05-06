/**
 * 
 */
package com.booking.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.model.Role;
import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.repository.RoleRepository;
import com.booking.repository.UserRoleRepository;

/**
 * @author ddung
 *
 */
public class UserContext implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserRoleRepository userRoleRepository;
	@Autowired
	private RoleRepository roleRepository;

	private User user;
	private boolean isSignin;
	private String permission;

	public UserContext setUserContext(UserContext userContext, User user, boolean isSignin) {
		userContext.setUser(user);
		userContext.setSignin(isSignin);
		if (user != null) {
			List<Role> roles = new ArrayList<Role>();
			List<UserRole> userRoles = userRoleRepository.findByUserId(user.getUserId());
			for (UserRole userRole : userRoles) {
				roles.add(roleRepository.findByRoleId(userRole.getRoleId()));
			}
			userContext.setPermission(AuthServiceUtil.getPermissionValue(roles));
		}
		return userContext;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isSignin() {
		return isSignin;
	}

	public void setSignin(boolean isSignin) {
		this.isSignin = isSignin;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public boolean hasPermission(String label) {

		int tmp = 0;
		int value = 0;

		if (permission.equals("user")) {
			tmp = 1;
		} else if (permission.equals("admin")) {
			tmp = 2;
		}

		switch (label) {
		case "user":
			value = 1;
			break;
		case "admin":
			value = 2;
			break;
		default:
			break;
		}

		if (tmp >= value) {
			return true;
		}

		return false;
	}

}
