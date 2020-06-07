/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
public interface UserRepository {
	public Iterable<User> getUsers(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer start, Integer end);

	public List<User> getUsersByUserRole(String username, String email, String phone, String firstName, String lastName,
			Integer age, Integer isHost, Integer isEnabled, Integer roleId, Integer start, Integer end);

	public User updateUser(User user);

	public User createUser(User user);

	public User deleteUser(long userId);

	public User findByUserId(long userId);

	public User findByUserName(String username);
}
