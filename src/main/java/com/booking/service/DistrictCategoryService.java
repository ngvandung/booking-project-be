/**
 * 
 */
package com.booking.service;

import com.booking.model.DistrictCategory;

/**
 * @author ddung
 *
 */
public interface DistrictCategoryService {
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId, Integer start,
			Integer end);

	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive, long cityId, long userId);

	public DistrictCategory createDistrictCategory(String districtName, long cityId, long userId);

	public DistrictCategory deleteDistrictCategory(long districtId);

	public DistrictCategory findById(long districtId);
	
	public void indexing();
}
