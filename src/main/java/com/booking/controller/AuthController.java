/**
 * 
 */
package com.booking.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.AuthBusiness;
import com.booking.model.User;
import com.booking.model.UserRole;
import com.booking.service.UserRoleService;
import com.booking.service.impl.JWTService;
import com.booking.util.AuthServiceUtil;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Autowired
	private AuthBusiness authBusiness;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private JWTService jwtService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> login(HttpServletRequest request, HttpSession session, @RequestBody User user) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("code", "400");
		result.put("message", "Login failed");

		String username = user.getUsername();
		String password = user.getPassword();

		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return result;
		}

		User _user = authBusiness.login(username, password);
		if (_user != null) {
			List<UserRole> userRoles = userRoleService.findByUserId(_user.getUserId());
			String roleName = AuthServiceUtil.getPermissionValueFromUserRole(userRoles);
			result.put("code", "200");
			result.put("message", "Login successfully");
			result.put("token", Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
			result.put("jwtToken", jwtService.generateTokenLogin(username));
			result.put("username", _user.getUsername());
			result.put("roleName", roleName);
			result.put("userId", String.valueOf(_user.getUserId())); 
			result.put("isHost", String.valueOf(_user.getIsHost()));
		}

		return result;

	}
}
