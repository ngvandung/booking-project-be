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
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Integer start,
			Integer end);

	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive, long userId);

	public DistrictCategory createDistrictCategory(String districtName, long userId);

	public DistrictCategory deleteDistrictCategory(long districtId);

	public DistrictCategory findById(long districtId);
}
