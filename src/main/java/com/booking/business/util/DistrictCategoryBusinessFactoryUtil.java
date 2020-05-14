/**
 * 
 */
package com.booking.business.util;

import com.booking.business.DistrictCategoryBusiness;
import com.booking.model.DistrictCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class DistrictCategoryBusinessFactoryUtil {
	// Design pattern - Singleton
	public static DistrictCategoryBusiness _districtCategoryBusiness;

	public static DistrictCategoryBusiness getDistrictCategoryBusiness() {

		if (_districtCategoryBusiness == null) {
			_districtCategoryBusiness = BeanUtil.getBean(DistrictCategoryBusiness.class);
		}
		return _districtCategoryBusiness;
	}
	// ============================

	public static Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId,
			Integer start, Integer end) {
		return getDistrictCategoryBusiness().getDistrictCategories(districtName, isActive, cityId, start, end);
	}

	public static DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive,
			long cityId, UserContext userContext) {
		return getDistrictCategoryBusiness().updateDistrictCategory(districtId, districtName, isActive, cityId,
				userContext);
	}

	public static DistrictCategory createDistrictCategory(String districtName, long cityId, UserContext userContext) {
		return getDistrictCategoryBusiness().createDistrictCategory(districtName, cityId, userContext);
	}

	public static DistrictCategory deleteDistrictCategory(long districtId, UserContext userContext) {
		return getDistrictCategoryBusiness().deleteDistrictCategory(districtId, userContext);
	}

	public static DistrictCategory findById(long districtId) {
		return getDistrictCategoryBusiness().findById(districtId);
	}
}
