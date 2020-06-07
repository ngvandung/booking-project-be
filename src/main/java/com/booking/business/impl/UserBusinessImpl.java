/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.booking.business.UserBusiness;
import com.booking.constant.RoleConstant;
import com.booking.constant.UserConstant;
import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.UserRoleService;
import com.booking.service.UserService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class UserBusinessImpl implements UserBusiness {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;

	@Override
	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end, UserContext userContext) {

		PermissionCheckerFactoryUtil.checkManagerOrAdministrator(userContext);

		return userService.getUsers(username, email, phone, firstName, lastName, age, isHost, isEnabled, start, end);
	}

	@Override
	public User updateUser(long userId, String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost, int isEnabled,
			UserContext userContext) {
		if (PermissionCheckerFactoryUtil.isOwner(userContext, userId)) {
			User user = userService.findByUserId(userId);

			User tmp = userService.findByUserName(username);
			if (tmp != null && user.getUserId() != tmp.getUserId()) {
				throw new DuplicateKeyException(username);
			}

			return userService.updateUser(userId, username, password, email, phone, firstName, lastName, age, address,
					birthDay, description, isHost, isEnabled);

		}
		return null;
	}

	@Override
	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost) {

		User tmp = userService.findByUserName(username);

		if (tmp != null) {
			throw new DuplicateKeyException(username);
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int isEnabled = UserConstant.ACTIVE;
		if (isHost == 1) {
			isEnabled = UserConstant.PENDING;
		}

		User user = userService.createUser(username, passwordEncoder.encode(password), email, phone, firstName,
				lastName, age, address, birthDay, description, isHost, isEnabled);

		if (user != null && isHost == 1) {
			isEnabled = UserConstant.PENDING;
			UserRole userRole = new UserRole();
			userRole.setRoleId(RoleConstant.HOST);
			userRole.setUserId(user.getUserId());

			userRole = userRoleService.createUserRole(userRole);
		}

		return user;
	}

	@Override
	public User deleteUser(long userId, UserContext userContext) {

		if (PermissionCheckerFactoryUtil.isOwner(userContext, userId)) {

			return userService.deleteUser(userId);
		}
		return null;
	}

	@Override
	public List<User> getUsersByUserRole(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer roleId, Integer start, Integer end,
			UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return userService.getUsersByUserRole(username, email, phone, firstName, lastName, age, isHost, isEnabled,
				roleId, start, end);
	}

	@Override
	public User activeUser(long userId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkManager(userContext);

		User user = userService.findByUserId(userId);
		if (user != null) {
			user.setIsEnabled(UserConstant.ACTIVE);

			user = userService.updateUser(user);
		}

		return user;
	}
}
