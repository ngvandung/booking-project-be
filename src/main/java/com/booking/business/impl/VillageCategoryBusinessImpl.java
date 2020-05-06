/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.VillageCategoryBusiness;
import com.booking.model.VillageCategory;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.VillageCategoryService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class VillageCategoryBusinessImpl implements VillageCategoryBusiness {
	@Autowired
	private VillageCategoryService villageCategoryService;

	@Override
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Integer start,
			Integer end) {
		return villageCategoryService.getVillageCategories(villageName, isActive, start, end);
	}

	@Override
	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive,
			UserContext userContext) {
		VillageCategory villageCategory = villageCategoryService.findById(villageId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, villageCategory.getUserId())) {
			villageCategory = villageCategoryService.updateVillageCategory(villageId, villageName, isActive,
					userContext.getUser().getUserId());
		}
		return villageCategory;
	}

	@Override
	public VillageCategory createVillageCategory(String villageName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return villageCategoryService.createVillageCategory(villageName, userContext.getUser().getUserId());
	}

	@Override
	public VillageCategory deleteVillageCategory(long villageId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return villageCategoryService.deleteVillageCategory(villageId);
	}

	@Override
	public VillageCategory findById(long villageId) {
		return villageCategoryService.findById(villageId);
	}
}
