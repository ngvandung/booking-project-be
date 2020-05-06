/**
 * 
 */
package com.booking.util;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ddung
 *
 */
public class ApplicationContext {
	@Autowired
	public static UserContext userContext;

	public static UserContext getUserContext() {
		return userContext;
	}

	public static void setUserContext(UserContext context) {
		userContext = context;

	}
}
