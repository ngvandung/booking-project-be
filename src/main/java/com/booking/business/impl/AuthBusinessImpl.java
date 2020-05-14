/**
 * 
 */
package com.booking.business.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.booking.business.AuthBusiness;
import com.booking.model.User;
import com.booking.service.UserService;

/**
 * @author ddung
 *
 */
public class AuthBusinessImpl implements AuthBusiness {

	private static final Logger log = Logger.getLogger(AuthBusinessImpl.class);
	@Autowired
	private UserService userService;

	@Override
	public User login(String username, String password) {
		User user = userService.findByUserName(username);

		if (user != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(password, user.getPassword())) {
				return user;
			}
		}

		return null;
	}

}
