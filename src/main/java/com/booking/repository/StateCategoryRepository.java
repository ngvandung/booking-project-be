/**
 * 
 */
package com.booking.repository;

import com.booking.model.StateCategory;

/**
 * @author ddung
 *
 */
public interface StateCategoryRepository {
	public StateCategory findById(long stateId);

	public StateCategory updateStateCategory(StateCategory stateCategory);

	public StateCategory createStateCategory(StateCategory stateCategory);

	public StateCategory deleteStateCategory(long stateId);
}
