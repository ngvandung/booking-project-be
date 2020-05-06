/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.booking.business.UserBusiness;
import com.booking.constant.RoleConstant;
import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.repository.UserRoleRepository;
import com.booking.security.PermissionCheckerFactoryUtil;
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
	private UserRoleRepository userRoleRepository;

	@Override
	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end, UserContext userContext) {

		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return userService.getUsers(username, email, phone, firstName, lastName, age, isHost, isEnabled, start, end);
	}

	@Override
	public User updateUser(long userId, String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description, int isEnabled,
			UserContext userContext) {
		if (PermissionCheckerFactoryUtil.isOwner(userContext, userId)) {
			User user = userService.findByUserId(userId);

			User tmp = userService.findByUserName(username);
			if (tmp != null && user.getUserId() != tmp.getUserId()) {
				throw new DuplicateKeyException(username);
			}

			return userService.updateUser(userId, username, password, email, phone, firstName, lastName, age, address,
					isHost, birthDay, description, isEnabled);

		}
		return null;
	}

	@Override
	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description) {

		User tmp = userService.findByUserName(username);

		if (tmp != null) {
			throw new DuplicateKeyException(username);
		}

		User user = userService.createUser(username, password, email, phone, firstName, lastName, age, address, isHost,
				birthDay, description);
		if (user != null) {
			// Default new user is role USER
			UserRole userRole = new UserRole();
			userRole.setRoleId(RoleConstant.USER);
			userRole.setUserId(user.getUserId());
			userRoleRepository.createUserRole(userRole);

			return user;
		}
		return null;
	}

	@Override
	public User deleteUser(long userId, UserContext userContext) {

		if (PermissionCheckerFactoryUtil.isOwner(userContext, userId)) {

			return userService.deleteUser(userId);
		}
		return null;
	}

}
