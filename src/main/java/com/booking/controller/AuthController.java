/**
 * 
 */
package com.booking.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.AuthBusiness;
import com.booking.model.User;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class AuthController {

	private static final Logger log = Logger.getLogger(AuthController.class);

	@Autowired
	private AuthBusiness authBusiness;

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
			result.put("code", "200");
			result.put("message", "Login successfully");
			result.put("token", Base64.getEncoder().encodeToString((username + ":" + password).getBytes()));
			result.put("username", _user.getUsername());
		}

		return result;

	}
}
