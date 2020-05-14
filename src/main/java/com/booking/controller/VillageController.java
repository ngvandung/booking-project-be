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

import com.booking.business.util.VillageCategoryBusinessFactoryUtil;
import com.booking.model.VillageCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class VillageController {

	@RequestMapping(value = "/village", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public VillageCategory createVillageCategory(HttpServletRequest request, HttpSession session,
			@RequestBody VillageCategory villageCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return VillageCategoryBusinessFactoryUtil.createVillageCategory(villageCategory.getVillageName(),
				villageCategory.getDistrictId(), userContext);

	}

	@RequestMapping(value = "/village", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public VillageCategory updateVillageCategory(HttpServletRequest request, HttpSession session,
			@RequestBody VillageCategory villageCategory) {

		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");
		return VillageCategoryBusinessFactoryUtil.updateVillageCategory(villageCategory.getVillageId(),
				villageCategory.getVillageName(), villageCategory.getIsActive(), villageCategory.getDistrictId(),
				userContext);

	}

	@RequestMapping(value = "/villages", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<VillageCategory> getVillageCategory(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "villageName", required = false) String villageName,
			@RequestParam(name = "isActive", required = false) Integer isActive,
			@RequestParam(name = "districtId", required = false) Long districtId,
			@RequestParam(name = "start", required = false) Integer start,
			@RequestParam(name = "end", required = false) Integer end) {

		return VillageCategoryBusinessFactoryUtil.getVillageCategories(villageName, isActive, districtId, start, end);

	}
}
