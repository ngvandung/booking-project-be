/**
 * 
 */
package com.booking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.UserRoleBusinessFactoryUtil;
import com.booking.model.UserRole;

import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1/admin")
public class UserRoleController {
	@RequestMapping(value = "/userrole", method = RequestMethod.PUT)
	@ResponseBody
	public UserRole updateUser(HttpServletRequest request, HttpSession session, @RequestBody UserRole userRoleModel) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserRoleBusinessFactoryUtil.updateUserRole(userRoleModel.getRoleId(), userRoleModel.getUserId(),
				userContext);
	}
}
