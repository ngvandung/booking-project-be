/**
 * 
 */
package com.booking.repository;

import com.booking.model.VillageCategory;

/**
 * @author ddung
 *
 */
public interface VillageCategoryRepository {
	public VillageCategory findById(long villageId);

	public VillageCategory updateVillageCategory(VillageCategory villageCategory);

	public VillageCategory createVillageCategory(VillageCategory villageCategory);

	public VillageCategory deleteVillageCategory(long villageId);
}
