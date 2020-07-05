/**
 * 
 */
package com.booking.business;

import com.booking.model.VillageCategory;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface VillageCategoryBusiness {
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Long districtId,
			Integer start, Integer end);

	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive, long districtId,
			UserContext userContext);

	public VillageCategory createVillageCategory(String villageName, long districtId, UserContext userContext);

	public VillageCategory deleteVillageCategory(long villageId, UserContext userContext);

	public VillageCategory findById(long villageId);
	
	public void indexing(UserContext userContext);
}
