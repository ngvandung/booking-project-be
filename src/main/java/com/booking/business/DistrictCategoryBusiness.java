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
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Integer start,
			Integer end);

	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive,
			UserContext userContext);

	public DistrictCategory createDistrictCategory(String districtName, UserContext userContext);

	public DistrictCategory deleteDistrictCategory(long districtId, UserContext userContext);

	public DistrictCategory findById(long districtId);
}
