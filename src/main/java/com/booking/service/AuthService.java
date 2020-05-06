/**
 * 
 */
package com.booking.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ddung
 *
 */
public interface AuthService {
	public String findLoggedInUserName();

	public String doLogin(HttpServletRequest request, HttpSession session, String userName, String password);

	public boolean isSignin(HttpServletRequest request, HttpSession httpSession);

}
