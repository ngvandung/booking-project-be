/**
 * 
 */
package com.booking.interceptor;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.booking.service.AuthService;
import com.booking.service.impl.JWTService;

/**
 * @author ddung
 *
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AuthService authService;
	@Autowired
	private JWTService jwtService;

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
			} else {
				if (jwtService.validateTokenLogin(authorization)) {
					String username = jwtService.getUsernameFromToken(authorization);
					String password = jwtService.getPasswordFromToken(authorization);

					if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
						authService.doLogin(request, session, username, password);
					}
				}
			}
		}

		return true;
	}
}
