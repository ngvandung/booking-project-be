/**
 * 
 */
package com.booking.business;

import com.booking.model.DistrictCategory;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface DistrictCategoryBusiness {
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId, Integer start,
			Integer end);

	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive, long cityId,
			UserContext userContext);

	public DistrictCategory createDistrictCategory(String districtName, long cityId, UserContext userContext);

	public DistrictCategory deleteDistrictCategory(long districtId, UserContext userContext);

	public DistrictCategory findById(long districtId);
}
