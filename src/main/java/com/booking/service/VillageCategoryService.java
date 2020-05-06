/**
 * 
 */
package com.booking.service;

import com.booking.model.VillageCategory;

/**
 * @author ddung
 *
 */
public interface VillageCategoryService {
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Integer start,
			Integer end);

	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive, long userId);

	public VillageCategory createVillageCategory(String villageName, long userId);

	public VillageCategory deleteVillageCategory(long villageId);

	public VillageCategory findById(long villageId);
}
