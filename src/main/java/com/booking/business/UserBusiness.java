/**
 * 
 */
package com.booking.business;

import java.util.Date;
import java.util.List;

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
			String lastName, int age, String address, Date birthDay, String description, Integer isHost, int isEnabled,
			String hashSecret, String tmnCode, UserContext userContext);

	public List<User> getUsersByUserRole(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer roleId, Integer start, Integer end,
			UserContext userContext);

	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost, String avatar);

	public User deleteUser(long userId, UserContext userContext);

	public User activeUser(long userId, UserContext userContext);

	public User findById(long userId, UserContext userContext);

	public User changePassword(long userId, String currentPassword, String newPassword, String confirmPassword,
			UserContext userContext);
	
	public User uploadAvatar(long userId, String avatar, UserContext userContext);
}
