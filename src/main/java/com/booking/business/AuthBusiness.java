/**
 * 
 */
package com.booking.business;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
public interface AuthBusiness {
	public User login(String username, String password);
}
