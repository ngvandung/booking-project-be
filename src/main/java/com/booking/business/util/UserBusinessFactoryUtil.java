/**
 * 
 */
package com.booking.business.util;

import java.util.Date;
import java.util.List;

import com.booking.business.UserBusiness;
import com.booking.model.User;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class UserBusinessFactoryUtil {

	// Design pattern - Singleton
	public static UserBusiness _userBusiness;

	public static UserBusiness getUserBusiness() {

		if (_userBusiness == null) {
			_userBusiness = BeanUtil.getBean(UserBusiness.class);
		}
		return _userBusiness;
	}
	// ============================

	public static Iterable<User> getUsers(String username, String email, String phone, String firstName,
			String lastName, Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end,
			UserContext userContext) {
		return getUserBusiness().getUsers(username, email, phone, firstName, lastName, age, isHost, isEnabled, start,
				end, userContext);
	}

	public static User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost,
			String avatar) {
		return getUserBusiness().createUser(username, password, email, phone, firstName, lastName, age, address,
				birthDay, description, isHost, avatar);
	}

	public static User updateUser(long userId, String username, String password, String email, String phone,
			String firstName, String lastName, int age, String address, Date birthDay, String description,
			Integer isHost, int isEnabled, String hashSecret, String tmnCode, UserContext userContext) {
		return getUserBusiness().updateUser(userId, username, password, email, phone, firstName, lastName, age, address,
				birthDay, description, isHost, isEnabled, hashSecret, tmnCode, userContext);
	}

	public static User deleteUser(long userId, UserContext userContext) {
		return getUserBusiness().deleteUser(userId, userContext);
	}

	public static User findById(long userId, UserContext userContext) {
		return getUserBusiness().findById(userId, userContext);
	}

	public static List<User> getUsersByUserRole(String username, String email, String phone, String firstName,
			String lastName, Integer age, Integer isHost, Integer isEnabled, Integer roleId, Integer start, Integer end,
			UserContext userContext) {
		return getUserBusiness().getUsersByUserRole(username, email, phone, firstName, lastName, age, isHost, isEnabled,
				roleId, start, end, userContext);
	}

	public static User actionUser(long userId, int status, UserContext userContext) {
		return getUserBusiness().actionUser(userId, status, userContext);
	}

	public static User changePassword(long userId, String currentPassword, String newPassword, String confirmPassword,
			UserContext userContext) {
		return getUserBusiness().changePassword(userId, currentPassword, newPassword, confirmPassword, userContext);
	}

	public static User uploadAvatar(long userId, String avatar, UserContext userContext) {
		return getUserBusiness().uploadAvatar(userId, avatar, userContext);
	}
}
