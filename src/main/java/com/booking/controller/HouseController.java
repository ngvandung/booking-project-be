/**
 * 
 */
package com.booking.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.HouseBusinessFactoryUtil;
import com.booking.model.House;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class HouseController {

	@RequestMapping(value = "/house", method = RequestMethod.POST)
	@ResponseBody
	public House createHouse(HttpServletRequest request, HttpSession session, @RequestBody House house) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseBusinessFactoryUtil.createHouse(house.getName(), house.getHouseTypeId(), house.getTypeName(),
				house.getStateId(), house.getStateName(), house.getCityId(), house.getCityName(), house.getDistrictId(),
				house.getDistrictName(), house.getVillageId(), house.getVillageName(), house.getLinkGoogleMap(),
				house.getPrice(), house.getBedroom(), house.getLivingroom(), house.getBathroom(), house.getMaxGuest(),
				house.getExtensionCategoryDetailIds(), house.getDescription(), userContext.getUser().getUserId(),
				userContext);
	}

	@RequestMapping(value = "/house/indexing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexing(HttpServletRequest request, HttpSession session) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		HouseBusinessFactoryUtil.indexing(userContext);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 200);
		return result;
	}

	@RequestMapping(value = "/house", method = RequestMethod.PUT)
	@ResponseBody
	public House updateHouse(HttpServletRequest request, HttpSession session, @RequestBody House house) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseBusinessFactoryUtil.updateHouse(house.getHouseId(), house.getName(), house.getHouseTypeId(),
				house.getTypeName(), house.getStateId(), house.getStateName(), house.getCityId(), house.getCityName(),
				house.getDistrictId(), house.getDistrictName(), house.getVillageId(), house.getVillageName(),
				house.getLinkGoogleMap(), house.getPrice(), house.getBedroom(), house.getLivingroom(),
				house.getBathroom(), house.getMaxGuest(), house.getExtensionCategoryDetailIds(), house.getDescription(),
				house.getIsActive(), userContext.getUser().getUserId(), userContext);

	}

	@RequestMapping(value = "/house/{houseId}", method = RequestMethod.DELETE)
	@ResponseBody
	public House deleteHouse(HttpServletRequest request, HttpSession session, @PathVariable("houseId") Long houseId) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseBusinessFactoryUtil.deleteHouse(houseId, userContext);
	}

	@RequestMapping(value = "/house/{houseId}", method = RequestMethod.GET)
	@ResponseBody
	public House findById(HttpServletRequest request, HttpSession session, @PathVariable("houseId") Long houseId) {

		return HouseBusinessFactoryUtil.findById(houseId);
	}

	@RequestMapping(value = "/houses", method = RequestMethod.GET)
	@ResponseBody
	public List<Map<String, Object>> findMyHouses(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "ownerHouseId", required = false) Long ownerHouseId,
			@RequestParam(name = "flag", required = false) String flag) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseBusinessFactoryUtil.findMyHouses(ownerHouseId, flag, userContext);
	}

	@RequestMapping(value = "/house/action/{houseId}", method = RequestMethod.PUT)
	@ResponseBody
	public House actionHouse(HttpServletRequest request, HttpSession session, @PathVariable("houseId") Long houseId,
			@RequestParam("status") int status) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseBusinessFactoryUtil.actionHouse(houseId, status, userContext);
	}

}
