/**
 * 
 */
package com.booking.repository;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
public interface UserDetailsRepository {
	User findUserByUsername(String username);
}
