/**
 * 
 */
package com.booking.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.StateCategoryBusinessFactoryUtil;
import com.booking.model.StateCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class StateController {
	@RequestMapping(value = "/state", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public StateCategory createStateCategory(HttpServletRequest request, HttpSession session,
			@RequestBody StateCategory stateCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		return StateCategoryBusinessFactoryUtil.createStateCategory(stateCategory.getStateName(), userContext);

	}

	@RequestMapping(value = "/state/indexing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexing(HttpServletRequest request, HttpSession session) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		StateCategoryBusinessFactoryUtil.indexing(userContext);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 200);
		return result;

	}

	@RequestMapping(value = "/states", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<StateCategory> getStateCategories(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "stateName", required = false) String stateName,
			@RequestParam(name = "isActive", required = false) Integer isActive,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		return StateCategoryBusinessFactoryUtil.getStateCategories(stateName, isActive, start, end);

	}
}
