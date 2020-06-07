/**
 * 
 */
package com.booking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.CityCategoryBusinessFactoryUtil;
import com.booking.model.CityCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class CityCategoryController {
	@RequestMapping(value = "/citys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<CityCategory> getCityCategories(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "cityName", required = false) String cityName,
			@RequestParam(name = "stateId", required = false) Long stateId,
			@RequestParam(name = "isActive", required = false) Integer isActive,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		return CityCategoryBusinessFactoryUtil.getCityCategories(cityName, isActive, stateId, start, end);

	}

	@RequestMapping(value = "/city", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public CityCategory createCityCategory(HttpServletRequest request, HttpSession session,
			@RequestBody CityCategory cityCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		return CityCategoryBusinessFactoryUtil.createCityCategory(cityCategory.getCityName(), cityCategory.getStateId(),
				userContext);

	}

	@RequestMapping(value = "/city", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public CityCategory updateCityCategory(HttpServletRequest request, HttpSession session,
			@RequestBody CityCategory cityCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		return CityCategoryBusinessFactoryUtil.updateCityCategory(cityCategory.getCityId(), cityCategory.getCityName(),
				1, cityCategory.getStateId(), userContext);

	}
}
