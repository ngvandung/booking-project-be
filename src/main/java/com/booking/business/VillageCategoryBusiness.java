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
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Integer start,
			Integer end);

	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive, UserContext userContext);

	public VillageCategory createVillageCategory(String villageName, UserContext userContext);

	public VillageCategory deleteVillageCategory(long villageId, UserContext userContext);

	public VillageCategory findById(long villageId);
}
