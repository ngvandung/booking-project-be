/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.DistrictCategory;

/**
 * @author ddung
 *
 */
public interface DistrictCategoryRepository {
	
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId, Integer start, Integer end);
	
	public DistrictCategory findById(long districtCategoryId);

	public DistrictCategory updateDistrictCategory(DistrictCategory districtCategory);

	public DistrictCategory createDistrictCategory(DistrictCategory districtCategory);

	public DistrictCategory deleteDistrictCategory(long districtCategoryId);
	
	public List<DistrictCategory> findAll();
}
