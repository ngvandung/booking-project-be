/**
 * 
 */
package com.booking.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.booking.constant.RoleConstant;
import com.booking.model.Role;
import com.booking.model.User;
import com.booking.model.UserRole;

/**
 * @author ddung
 *
 */
public class AuthServiceUtil {
	public enum DefaultRole {
		ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), HOST("ROLE_HOST");

		private String value;

		private DefaultRole(String value) {
			this.value = value;
		}

		public String getValue() {

			return value;
		}

		@Override
		public String toString() {

			return this.getValue();
		}

	}

	public enum SigninMessage {
		INCORECT_USERNAME("incorect-username"), INCORECT_PASSWORD("incorect-password"),
		SIGNIN_SUCCESS("signin-success"), SIGNIN_FAILED("signin-failed");

		private String message;

		private SigninMessage(String message) {
			this.message = message;
		}

		public String getMessage() {

			return message;
		}

		@Override
		public String toString() {

			return this.getMessage();
		}
	}

	public static String getPermissionValueFromRole(List<Role> roles) {
		String permission = "";
		if (roles != null) {
			for (Role role : roles) {
				if (role.getRoleName().equals(DefaultRole.ADMIN.toString())) {
					permission = DefaultRole.ADMIN.toString();
					break;
				} else if (role.getRoleName().equals(DefaultRole.MANAGER.toString())) {
					permission = DefaultRole.MANAGER.toString();
					break;
				} else if (role.getRoleName().equals(DefaultRole.HOST.toString())) {
					permission = DefaultRole.HOST.toString();
					break;
				} else if (role.getRoleName().equals(DefaultRole.USER.toString())) {
					permission = DefaultRole.USER.toString();
				}
			}
		}
		return permission;
	}

	public static String getPermissionValueFromUserRole(List<UserRole> userRoles) {
		String permission = "";
		if (userRoles != null) {
			for (UserRole userRole : userRoles) {
				if (userRole.getRoleId() == RoleConstant.ADMIN) {
					permission = DefaultRole.ADMIN.toString();
					break;
				} else if (userRole.getRoleId() == RoleConstant.MANAGER) {
					permission = DefaultRole.MANAGER.toString();
					break;
				} else if (userRole.getRoleId() == RoleConstant.HOST) {
					permission = DefaultRole.HOST.toString();
					break;
				} else if (userRole.getRoleId() == RoleConstant.USER) {
					permission = DefaultRole.USER.toString();
				}
			}
		}
		return permission;
	}

	public static void store(User user, HttpServletRequest request, HttpSession session) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = userContext.setUserContext(userContext, user, true);

		ApplicationContext.setUserContext(userContext);

		request.setAttribute("userContext", userContext);

		session.setAttribute("isSignin", true);

		session.setAttribute("userContext", userContext);

		session.setMaxInactiveInterval(900);
	}

	public static void close(HttpServletRequest request, HttpSession session) {

		ApplicationContext.setUserContext(null);

		request.setAttribute("userContext", null);
	}

}
