/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.VillageCategory;

/**
 * @author ddung
 *
 */
public interface VillageCategoryRepository {

	public Iterable<VillageCategory> getVillageCategories(String villageName, Integer isActive, Long districtId, Integer start,
			Integer end);

	public VillageCategory findById(long villageId);

	public VillageCategory updateVillageCategory(VillageCategory villageCategory);

	public VillageCategory createVillageCategory(VillageCategory villageCategory);

	public VillageCategory deleteVillageCategory(long villageId);
	
	public List<VillageCategory> findAll();
}
