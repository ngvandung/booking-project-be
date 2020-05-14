/**
 * 
 */
package com.booking.interceptor;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.booking.service.AuthService;

/**
 * @author ddung
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger log = Logger.getLogger(LogInterceptor.class);
	@Autowired
	private AuthService authService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String authorization = request.getHeader("Authorization");

		HttpSession session = request.getSession();

		if (authorization != null) {
			if (authorization.contains("Basic")) {
				String token = authorization.replace("Basic", "").trim();
				byte[] bt = Base64.getDecoder().decode(token);
				String[] usename_password = (new String(bt)).split(":");
				if (usename_password != null && usename_password.length >= 2) {
					authService.doLogin(request, session, usename_password[0], usename_password[1]);
				}
			}
		}

		return true;
	}
}
