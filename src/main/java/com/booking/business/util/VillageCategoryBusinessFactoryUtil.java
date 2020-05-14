/**
 * 
 */
package com.booking.business.util;

import com.booking.business.VillageCategoryBusiness;
import com.booking.model.VillageCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class VillageCategoryBusinessFactoryUtil {
	// Design pattern - Singleton
	public static VillageCategoryBusiness _villageCategoryBusiness;

	public static VillageCategoryBusiness getVillageCategoryBusiness() {

		if (_villageCategoryBusiness == null) {
			_villageCategoryBusiness = BeanUtil.getBean(VillageCategoryBusiness.class);
		}
		return _villageCategoryBusiness;
	}
	// ============================

	public static Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Long districtId,
			Integer start, Integer end) {
		return getVillageCategoryBusiness().getVillageCategories(villageName, isActive, districtId, start, end);
	}

	public static VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive,
			long districtId, UserContext userContext) {
		return getVillageCategoryBusiness().updateVillageCategory(villageId, villageName, isActive, districtId,
				userContext);
	}

	public static VillageCategory createVillageCategory(String villageName, long districtId, UserContext userContext) {
		return getVillageCategoryBusiness().createVillageCategory(villageName, districtId, userContext);
	}

	public static VillageCategory deleteVillageCategory(long villageId, UserContext userContext) {
		return getVillageCategoryBusiness().deleteVillageCategory(villageId, userContext);
	}

	public static VillageCategory findById(long villageId) {
		return getVillageCategoryBusiness().findById(villageId);
	}
}
