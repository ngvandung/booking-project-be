/**
 * 
 */
package com.booking.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.booking.model.User;
import com.booking.service.AuthService;
import com.booking.service.UserService;
import com.booking.util.AuthServiceUtil;

/**
 * @author ddung
 *
 */
@Service
public class AuthServiceImpl implements AuthService {
	
	private static final Logger log = Logger.getLogger(AuthServiceImpl.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Override
	public String findLoggedInUserName() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}

	@Override
	public String doLogin(HttpServletRequest request, HttpSession session, String userName, String password) {

		User user = userService.findByUserName(userName);

		if (user == null) {
			return AuthServiceUtil.SigninMessage.INCORECT_USERNAME.getMessage();
		}

		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

		if (userDetails == null) {
			return AuthServiceUtil.SigninMessage.INCORECT_USERNAME.getMessage();
		}

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {

			try {
				authenticationManager.authenticate(usernamePasswordAuthenticationToken);

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				AuthServiceUtil.store(user, request, session);

				return AuthServiceUtil.SigninMessage.SIGNIN_SUCCESS.getMessage();

			} catch (Exception e) {
				log.error(e);

				return AuthServiceUtil.SigninMessage.INCORECT_PASSWORD.getMessage();
			}

		} else {

			return AuthServiceUtil.SigninMessage.SIGNIN_FAILED.getMessage();
		}

	}

	@Override
	public boolean isSignin(HttpServletRequest request, HttpSession httpSession) {
		boolean isSigned = false;
		try {
			isSigned = Boolean.parseBoolean(httpSession.getAttribute("isSignin").toString())
					&& request.getUserPrincipal() != null && (request.getUserPrincipal().getName() != null);
		} catch (Exception e) {
			log.error(e);
		}

		return isSigned;

	}

}
