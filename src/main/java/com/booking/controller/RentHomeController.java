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

import com.booking.business.util.RentHomeBusinessFactoryUtil;
import com.booking.model.RentHome;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class RentHomeController {
	@RequestMapping(value = "/renthome/{homeId}", method = RequestMethod.POST)
	@ResponseBody
	public RentHome createRentHome(HttpServletRequest request, HttpSession session, @RequestBody RentHome rentHome,
			@PathVariable("homeId") Long homeId) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return RentHomeBusinessFactoryUtil.createRentHome(rentHome.getRentPeople(), rentHome.getFromDate(),
				rentHome.getToDate(), homeId, rentHome.getRentUserId(), userContext);

	}

	@RequestMapping(value = "/renthome", method = RequestMethod.PUT)
	@ResponseBody
	public RentHome updateRentHome(HttpServletRequest request, HttpSession session, @RequestBody RentHome rentHome) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return RentHomeBusinessFactoryUtil.updateRentHome(rentHome.getRentId(), rentHome.getRentPeople(),
				rentHome.getFromDate(), rentHome.getToDate(), rentHome.getHomeId(), rentHome.getRentUserId(),
				userContext);

	}

	@RequestMapping(value = "/renthome/{rentId}", method = RequestMethod.DELETE)
	@ResponseBody
	public RentHome deleteRentHome(HttpServletRequest request, HttpSession session,
			@PathVariable("rentId") Long rentId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return RentHomeBusinessFactoryUtil.deleteRentHome(rentId, userContext);
	}
}
