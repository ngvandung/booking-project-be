/**
 * 
 */
package com.booking.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.HouseTypeBusinessFactoryUtil;
import com.booking.model.HouseType;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class HouseTypeController {
	@RequestMapping(value = "/housetypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<HouseType> getHouseTypes(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "typeName", required = false) String typeName,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		Iterable<HouseType> houseTypes = HouseTypeBusinessFactoryUtil.getHouseTypes(typeName, start, end);

		return houseTypes;
	}

	@RequestMapping(value = "/housetype", method = RequestMethod.POST)
	@ResponseBody
	public HouseType createHouseType(HttpServletRequest request, HttpSession session, @RequestBody HouseType houseType)
			throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseTypeBusinessFactoryUtil.createHouseType(houseType.getTypeName(), userContext);

	}

	@RequestMapping(value = "/housetype/indexing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexing(HttpServletRequest request, HttpSession session) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		HouseTypeBusinessFactoryUtil.indexing(userContext);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 200);
		return result;

	}

	@RequestMapping(value = "/housetype", method = RequestMethod.PUT)
	@ResponseBody
	public HouseType updateHouseType(HttpServletRequest request, HttpSession session, @RequestBody HouseType houseType)
			throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseTypeBusinessFactoryUtil.updateHouseType(houseType.getHouseTypeId(), houseType.getTypeName(),
				userContext);

	}

	@RequestMapping(value = "/housetype/{houseTypeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public HouseType deleteHouseType(HttpServletRequest request, HttpSession session,
			@PathVariable("houseTypeId") Long houseTypeId) throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HouseTypeBusinessFactoryUtil.deleteHouseType(houseTypeId, userContext);
	}
}
