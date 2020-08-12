/**
 * 
 */
package com.booking.business.util;

import com.booking.business.StateCategoryBusiness;
import com.booking.model.StateCategory;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class StateCategoryBusinessFactoryUtil {
	// Design pattern - Singleton
	private static StateCategoryBusiness _stateCategoryBusiness;

	public static StateCategoryBusiness getStateCategoryBusiness() {

		if (_stateCategoryBusiness == null) {
			_stateCategoryBusiness = BeanUtil.getBean(StateCategoryBusiness.class);
		}
		return _stateCategoryBusiness;
	}
	// ============================

	public static Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start,
			Integer end) {
		return getStateCategoryBusiness().getStateCategories(stateName, isActive, start, end);
	}

	public static StateCategory updateStateCategory(long stateId, String stateName, Integer isActive,
			UserContext userContext) {
		return getStateCategoryBusiness().updateStateCategory(stateId, stateName, isActive, userContext);
	}

	public static StateCategory createStateCategory(String stateName, UserContext userContext) {
		return getStateCategoryBusiness().createStateCategory(stateName, userContext);
	}

	public static StateCategory deleteStateCategory(long stateId, UserContext userContext) {
		return getStateCategoryBusiness().deleteStateCategory(stateId, userContext);
	}

	public static StateCategory findById(long stateId) {
		return getStateCategoryBusiness().findById(stateId);
	}

	public static void indexing(UserContext userContext) {
		getStateCategoryBusiness().indexing(userContext);
	}
}
