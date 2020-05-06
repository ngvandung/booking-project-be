/**
 * 
 */
package com.booking.repository;

import com.booking.model.DistrictCategory;

/**
 * @author ddung
 *
 */
public interface DistrictCategoryRepository {
	public DistrictCategory findById(long districtCategoryId);

	public DistrictCategory updateDistrictCategory(DistrictCategory districtCategory);

	public DistrictCategory createDistrictCategory(DistrictCategory districtCategory);

	public DistrictCategory deleteDistrictCategory(long districtCategoryId);
}
