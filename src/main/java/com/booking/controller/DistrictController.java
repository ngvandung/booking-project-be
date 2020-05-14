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

import com.booking.business.util.DistrictCategoryBusinessFactoryUtil;
import com.booking.model.DistrictCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class DistrictController {

	@RequestMapping(value = "/district", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DistrictCategory createDistrictCategory(HttpServletRequest request, HttpSession session,
			@RequestBody DistrictCategory districtCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return DistrictCategoryBusinessFactoryUtil.createDistrictCategory(districtCategory.getDistrictName(),
				districtCategory.getCityId(), userContext);

	}

	@RequestMapping(value = "/district", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public DistrictCategory updateDistrictCategory(HttpServletRequest request, HttpSession session,
			@RequestBody DistrictCategory districtCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		return DistrictCategoryBusinessFactoryUtil.updateDistrictCategory(districtCategory.getDistrictId(),
				districtCategory.getDistrictName(), districtCategory.getIsActive(), districtCategory.getCityId(),
				userContext);

	}

	@RequestMapping(value = "/districts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<DistrictCategory> getDistrictCategories(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "districtName", required = false) String districtName,
			@RequestParam(name = "isActive", required = false) Integer isActive,
			@RequestParam(name = "cityId", required = false) Long cityId,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		return DistrictCategoryBusinessFactoryUtil.getDistrictCategories(districtName, isActive, cityId, start, end);

	}
}
