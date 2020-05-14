/**
 * 
 */
package com.booking.service;

import com.booking.model.CityCategory;

/**
 * @author ddung
 *
 */
public interface CityCategoryService {
	public Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Long stateId, Integer start,
			Integer end);

	public CityCategory updateCityCategory(long cityId, String cityName, Integer isActive, long stateId, long userId);

	public CityCategory createCityCategory(String cityName, long stateId, long userId);

	public CityCategory deleteCityCategory(long cityId);

	public CityCategory findById(long cityId);
}
