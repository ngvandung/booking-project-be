/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.StateCategoryBusiness;
import com.booking.model.StateCategory;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.StateCategoryService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class StateCategoryBusinessImpl implements StateCategoryBusiness {
	@Autowired
	private StateCategoryService stateCategoryService;

	@Override
	public Iterable<StateCategory> getStateCategories(String stateName, Integer isActive, Integer start, Integer end) {
		return stateCategoryService.getStateCategories(stateName, isActive, start, end);
	}

	@Override
	public StateCategory updateStateCategory(long stateId, String stateName, Integer isActive,
			UserContext userContext) {
		StateCategory stateCategory = stateCategoryService.findById(stateId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, stateCategory.getUserId())) {
			stateCategory = stateCategoryService.updateStateCategory(stateId, stateName, isActive,
					userContext.getUser().getUserId());
		}
		return stateCategory;
	}

	@Override
	public StateCategory createStateCategory(String stateName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return stateCategoryService.createStateCategory(stateName, userContext.getUser().getUserId());
	}

	@Override
	public StateCategory deleteStateCategory(long stateId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAdministrator(userContext);

		return stateCategoryService.deleteStateCategory(stateId);
	}

	@Override
	public StateCategory findById(long stateId) {
		return stateCategoryService.findById(stateId);
	}
}
