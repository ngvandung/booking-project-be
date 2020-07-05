/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.CityCategory;

/**
 * @author ddung
 *
 */
public interface CityCategoryRepository {

	public Iterable<CityCategory> getCityCategories(String cityName, Integer isActive, Long stateId, Integer start, Integer end);

	public CityCategory findById(long cityId);

	public CityCategory updateCityCategory(CityCategory cityCategory);

	public CityCategory createCityCategory(CityCategory cityCategory);

	public CityCategory deleteCityCategory(long cityId);

	public List<CityCategory> findAll();
}
