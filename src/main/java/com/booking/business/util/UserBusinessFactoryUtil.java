/**
 * 
 */
package com.booking.business.util;

import java.util.Date;

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

	public static Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end, UserContext userContext) {
		return getUserBusiness().getUsers(username, email, phone, firstName, lastName, age, isHost, isEnabled, start,
				end, userContext);
	}

	public static User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description) {
		return getUserBusiness().createUser(username, password, email, phone, firstName, lastName, age, address, isHost,
				birthDay, description);
	}

	public static User updateUser(long userId, String username, String password, String email, String phone,
			String firstName, String lastName, int age, String address, int isHost, Date birthDay, String description,
			int isEnabled, UserContext userContext) {
		return getUserBusiness().updateUser(userId, username, password, email, phone, firstName, lastName, age, address,
				isHost, birthDay, description, isEnabled, userContext);
	}

	public static User deleteUser(long userId, UserContext userContext) {
		return getUserBusiness().deleteUser(userId, userContext);
	}
}
