/**
 * 
 */
package com.booking.service;

import com.booking.model.StateCategory;

/**
 * @author ddung
 *
 */
public interface StateCategoryService {
	public Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start,
			Integer end);

	public StateCategory updateStateCategory(long stateId, String stateName, Integer isActive, long userId);

	public StateCategory createStateCategory(String stateName, long userId);

	public StateCategory deleteStateCategory(long stateId);

	public StateCategory findById(long stateId);
}
