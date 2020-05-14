/**
 * 
 */
package com.booking.service;

import java.util.Date;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
public interface UserService {

	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end);

	public User updateUser(long userId, String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description, int isEnabled);

	public User createUser(String username, String password, String email, String phone, String firstName,
			String lastName, int age, String address, int isHost, Date birthDay, String description, int isEnabled);

	public User deleteUser(long userId);

	public User findByUserName(String username);

	public User findByUserId(long userId);
}
