/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.booking.business.UserBusiness;
import com.booking.model.User;
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

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		int isEnabled = 0;
		if (isHost == 1) {
			isEnabled = 0;
		} else {
			isEnabled = 1;
		}

		User user = userService.createUser(username, passwordEncoder.encode(password), email, phone, firstName,
				lastName, age, address, isHost, birthDay, description, isEnabled);

		return user;
	}

	@Override
	public User deleteUser(long userId, UserContext userContext) {

		if (PermissionCheckerFactoryUtil.isOwner(userContext, userId)) {

			return userService.deleteUser(userId);
		}
		return null;
	}

}
