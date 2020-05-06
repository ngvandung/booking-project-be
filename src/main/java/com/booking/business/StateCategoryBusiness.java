/**
 * 
 */
package com.booking.business;

import com.booking.model.StateCategory;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface StateCategoryBusiness {
	public Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start,
			Integer end);

	public StateCategory updateStateCategory(long stateId, String stateName, Integer isActive, UserContext userContext);

	public StateCategory createStateCategory(String stateName, UserContext userContext);

	public StateCategory deleteStateCategory(long stateId, UserContext userContext);

	public StateCategory findById(long stateId);
}
