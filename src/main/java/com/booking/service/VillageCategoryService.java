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
	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Long districtId, Integer start,
			Integer end);

	public VillageCategory updateVillageCategory(long villageId, String villageName, Integer isActive, long districtId, long userId);

	public VillageCategory createVillageCategory(String villageName, long districtId, long userId);

	public VillageCategory deleteVillageCategory(long villageId);

	public VillageCategory findById(long villageId);
}
