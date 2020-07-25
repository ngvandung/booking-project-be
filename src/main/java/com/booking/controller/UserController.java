/**
 * 
 */
package com.booking.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.booking.business.util.UserBusinessFactoryUtil;
import com.booking.model.User;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<User> getUsers(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName,
			@RequestParam(name = "age", required = false) Integer age,
			@RequestParam(name = "isHost", required = false) Integer isHost,
			@RequestParam(name = "isEnabled", required = false) Integer isEnabled,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		Iterable<User> users = UserBusinessFactoryUtil.getUsers(username, email, phone, firstName, lastName, age,
				isHost, isEnabled, start, end, userContext);

		return users;
	}

	@RequestMapping(value = "/userrole/users", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getUsersByUserRole(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "email", required = false) String email,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "firstName", required = false) String firstName,
			@RequestParam(name = "lastName", required = false) String lastName,
			@RequestParam(name = "age", required = false) Integer age,
			@RequestParam(name = "isHost", required = false) Integer isHost,
			@RequestParam(name = "isEnabled", required = false) Integer isEnabled,
			@RequestParam(name = "roleId", required = false) Integer roleId,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.getUsersByUserRole(username, email, phone, firstName, lastName, age, isHost,
				isEnabled, roleId, start, end, userContext);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	@ResponseBody
	public User createUser(@RequestBody User user) {

		return UserBusinessFactoryUtil.createUser(user.getUsername(), user.getPassword(), user.getEmail(),
				user.getPhone(), user.getFirstName(), user.getLastName(), user.getAge(), user.getAddress(),
				user.getBirthDay(), user.getDescription(), user.getIsHost(), user.getAvatar());

	}

	@RequestMapping(value = "/user/{userId}/avatar", method = RequestMethod.POST)
	@ResponseBody
	public User uploadAvatar(HttpServletRequest request, HttpSession session, @PathVariable("userId") long userId,
			@RequestParam("file") MultipartFile file) throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		StringBuilder sb = new StringBuilder();
		sb.append("data:image/png;base64,");
		sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(file.getBytes(), false)));

		return UserBusinessFactoryUtil.uploadAvatar(userId, sb.toString(), userContext);

	}

	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	@ResponseBody
	public User updateUser(HttpServletRequest request, HttpSession session, @RequestBody User userModel) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.updateUser(userModel.getUserId(), userModel.getUsername(),
				userModel.getPassword(), userModel.getEmail(), userModel.getPhone(), userModel.getFirstName(),
				userModel.getLastName(), userModel.getAge(), userModel.getAddress(), userModel.getBirthDay(),
				userModel.getDescription(), userModel.getIsHost(), userModel.getIsEnabled(), userModel.getHashSecret(),
				userModel.getTmnCode(), userContext);

	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
	@ResponseBody
	public User changePassword(HttpServletRequest request, HttpSession session, @PathVariable("userId") long userId,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.changePassword(userId, currentPassword, newPassword, confirmPassword,
				userContext);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.DELETE)
	@ResponseBody
	public User deleteUser(HttpServletRequest request, HttpSession session, @PathVariable("userId") Long userId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.deleteUser(userId, userContext);
	}

	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	@ResponseBody
	public User getDetailUser(HttpServletRequest request, HttpSession session, @PathVariable("userId") Long userId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.findById(userId, userContext);
	}

	@RequestMapping(value = "/user/action/{userId}", method = RequestMethod.PUT)
	@ResponseBody
	public User activeUser(HttpServletRequest request, HttpSession session, @PathVariable("userId") Long userId,
			@RequestParam("status") int status) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return UserBusinessFactoryUtil.actionUser(userId, status, userContext);
	}
}
