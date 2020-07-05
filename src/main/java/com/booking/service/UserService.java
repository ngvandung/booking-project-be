/**
 * 
 */
package com.booking.service;

import java.util.Date;
import java.util.List;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
public interface UserService {

	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end);

	public List<User> getUsersByUserRole(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer roleId, Integer start, Integer end);

	public User updateUser(long userId, String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost, int isEnabled,
			String hashSecret, String tmnCode);

	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, Date birthDay, String description, Integer isHost, String avatar, int isEnabled);

	public User updateUser(User user);

	public User deleteUser(long userId);

	public User findByUserName(String username);

	public User findByUserId(long userId);
	
	public User uploadAvatar(long userId, String avatar);
}
