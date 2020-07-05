/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.DistrictCategoryBusiness;
import com.booking.model.DistrictCategory;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.DistrictCategoryService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class DistrictCategoryBusinessImpl implements DistrictCategoryBusiness {

	@Autowired
	private DistrictCategoryService districtCategoryService;

	@Override
	public Iterable<DistrictCategory> getDistrictCategories(String districtName, Integer isActive, Long cityId,
			Integer start, Integer end) {
		return districtCategoryService.getDistrictCategories(districtName, isActive, cityId, start, end);
	}

	@Override
	public DistrictCategory updateDistrictCategory(long districtId, String districtName, Integer isActive, long cityId,
			UserContext userContext) {
		DistrictCategory DistrictCategory = districtCategoryService.findById(districtId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, DistrictCategory.getUserId())) {
			DistrictCategory = districtCategoryService.updateDistrictCategory(districtId, districtName, isActive,
					cityId, userContext.getUser().getUserId());
		}
		return DistrictCategory;
	}

	@Override
	public DistrictCategory createDistrictCategory(String districtName, long cityId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return districtCategoryService.createDistrictCategory(districtName, cityId, userContext.getUser().getUserId());
	}

	@Override
	public DistrictCategory deleteDistrictCategory(long districtId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return districtCategoryService.deleteDistrictCategory(districtId);
	}

	@Override
	public DistrictCategory findById(long districtId) {
		return districtCategoryService.findById(districtId);
	}

	@Override
	public void indexing(UserContext userContext) {
		districtCategoryService.indexing();
	}

}
