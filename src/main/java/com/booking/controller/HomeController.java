/**
 * 
 */
package com.booking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.HomeBusinessFactoryUtil;
import com.booking.model.Home;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class HomeController {

	@RequestMapping(value = "/home", method = RequestMethod.POST)
	@ResponseBody
	public Home createHome(HttpServletRequest request, HttpSession session, @RequestBody Home home) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeBusinessFactoryUtil.createHome(home.getName(), home.getCategoryId(), home.getHomeTypeId(),
				home.getTypeName(), home.getStateId(), home.getStateName(), home.getCityId(), home.getCityName(),
				home.getDistrictId(), home.getDistrictName(), home.getVillageId(), home.getVillageName(),
				home.getLinkGoogleMap(), home.getPrice(), home.getBedroom(), home.getLivingroom(), home.getBathroom(),
				home.getMaxGuest(), home.getDescription(), userContext.getUser().getUserId(), userContext);

	}

	@RequestMapping(value = "/home", method = RequestMethod.PUT)
	@ResponseBody
	public Home updateHome(HttpServletRequest request, HttpSession session, @RequestBody Home home) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeBusinessFactoryUtil.updateHome(home.getHomeId(), home.getName(), home.getCategoryId(),
				home.getHomeTypeId(), home.getTypeName(), home.getStateId(), home.getStateName(), home.getCityId(),
				home.getCityName(), home.getDistrictId(), home.getDistrictName(), home.getVillageId(),
				home.getVillageName(), home.getLinkGoogleMap(), home.getPrice(), home.getBedroom(),
				home.getLivingroom(), home.getBathroom(), home.getMaxGuest(), home.getDescription(), home.getIsActive(),
				userContext.getUser().getUserId(), userContext);

	}

	@RequestMapping(value = "/home/{homeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public Home deleteHome(HttpServletRequest request, HttpSession session, @PathVariable("homeId") Long homeId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeBusinessFactoryUtil.deleteHome(homeId, userContext);
	}

	@RequestMapping(value = "/home/{homeId}", method = RequestMethod.GET)
	@ResponseBody
	public Home findById(HttpServletRequest request, HttpSession session, @PathVariable("homeId") Long homeId) {

		return HomeBusinessFactoryUtil.findById(homeId);
	}

	@RequestMapping(value = "/home/active/{homeId}", method = RequestMethod.PUT)
	@ResponseBody
	public Home activeHome(HttpServletRequest request, HttpSession session, @PathVariable("homeId") Long homeId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeBusinessFactoryUtil.activeHome(homeId, userContext);
	}
}
