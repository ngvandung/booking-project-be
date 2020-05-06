/**
 * 
 */

package com.booking.business.util;

import com.booking.business.CityCategoryBusiness;
import com.booking.model.CityCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class CityCategoryBusinessFactoryUtil {
	// Design pattern - Singleton
	public static CityCategoryBusiness _cityCategoryBusiness;

	public static CityCategoryBusiness getCityCategoryBusiness() {

		if (_cityCategoryBusiness == null) {
			_cityCategoryBusiness = BeanUtil.getBean(CityCategoryBusiness.class);
		}
		return _cityCategoryBusiness;
	} // ============================

	public static Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Integer start,
			Integer end) {
		return getCityCategoryBusiness().getCityCategories(cityName, isActive, start, end);
	}

	public static CityCategory updateCityCategory(long cityId, String cityName, Integer isActive,
			UserContext userContext) {
		return getCityCategoryBusiness().updateCityCategory(cityId, cityName, isActive, userContext);
	}

	public static CityCategory createCityCategory(String cityName, UserContext userContext) {
		return getCityCategoryBusiness().createCityCategory(cityName, userContext);
	}

	public static CityCategory deleteCityCategory(long cityId, UserContext userContext) {
		return getCityCategoryBusiness().deleteCityCategory(cityId, userContext);
	}

	public static CityCategory findById(long cityId) {
		return getCityCategoryBusiness().findById(cityId);
	}
}
