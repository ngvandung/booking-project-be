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

import com.booking.business.util.HomeTypeBusinessFactoryUtil;
import com.booking.model.HomeType;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class HomeTypeController {
	@RequestMapping(value = "/hometypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<HomeType> getHomeTypes(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "typeName", required = false) String typeName,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		Iterable<HomeType> homeTypes = HomeTypeBusinessFactoryUtil.getHomeTypes(typeName, start, end);

		return homeTypes;
	}

	@RequestMapping(value = "/hometype", method = RequestMethod.POST)
	@ResponseBody
	public HomeType createHomeType(HttpServletRequest request, HttpSession session, @RequestBody HomeType homeType)
			throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeTypeBusinessFactoryUtil.createHomeType(homeType.getTypeName(), userContext);

	}

	@RequestMapping(value = "/hometype/indexing", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> indexing(HttpServletRequest request, HttpSession session) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		HomeTypeBusinessFactoryUtil.indexing(userContext);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", 200);
		return result;

	}

	@RequestMapping(value = "/hometype", method = RequestMethod.PUT)
	@ResponseBody
	public HomeType updateHomeType(HttpServletRequest request, HttpSession session, @RequestBody HomeType homeType)
			throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeTypeBusinessFactoryUtil.updateHomeType(homeType.getHomeTypeId(), homeType.getTypeName(),
				userContext);

	}

	@RequestMapping(value = "/hometype/{homeTypeId}", method = RequestMethod.DELETE)
	@ResponseBody
	public HomeType deleteHomeType(HttpServletRequest request, HttpSession session,
			@PathVariable("homeTypeId") Long homeTypeId) throws IOException {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return HomeTypeBusinessFactoryUtil.deleteHomeType(homeTypeId, userContext);
	}
}
