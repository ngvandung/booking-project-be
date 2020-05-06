/**
 * 
 */
package com.booking.business;

import java.util.Date;

import com.booking.model.User;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface UserBusiness {
	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end, UserContext userContext);

	public User updateUser(long userId, String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description, int isEnabled,
			UserContext userContext);

	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description);

	public User deleteUser(long userId, UserContext userContext);
}
