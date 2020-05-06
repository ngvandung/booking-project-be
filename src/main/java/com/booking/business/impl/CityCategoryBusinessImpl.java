/**
 * 
 */

package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.CityCategoryBusiness;
import com.booking.model.CityCategory;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.CityCategoryService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class CityCategoryBusinessImpl implements CityCategoryBusiness {

	@Autowired
	private CityCategoryService cityCategoryService;

	@Override
	public Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Integer start, Integer end) {
		return cityCategoryService.getCityCategories(cityName, isActive, start, end);
	}

	@Override
	public CityCategory updateCityCategory(long cityId, String cityName, Integer isActive, UserContext userContext) {
		CityCategory cityCategory = cityCategoryService.findById(cityId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, cityCategory.getUserId())) {
			cityCategory = cityCategoryService.updateCityCategory(cityId, cityName, isActive,
					userContext.getUser().getUserId());
		}
		return cityCategory;
	}

	@Override
	public CityCategory createCityCategory(String cityName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return cityCategoryService.createCityCategory(cityName, userContext.getUser().getUserId());
	}

	@Override
	public CityCategory deleteCityCategory(long cityId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return cityCategoryService.deleteCityCategory(cityId);
	}

	@Override
	public CityCategory findById(long cityId) {
		return cityCategoryService.findById(cityId);
	}

}
