/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HomeTypeBusiness;
import com.booking.model.HomeType;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HomeTypeService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HomeTypeBusinessImpl implements HomeTypeBusiness {

	@Autowired
	private HomeTypeService homeTypeService;

	@Override
	public Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end) {
		return homeTypeService.getHomeTypes(typeName, start, end);
	}

	@Override
	public HomeType updateHomeType(long homeTypeId, String typeName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return homeTypeService.updateHomeType(homeTypeId, typeName, userContext.getUser().getUserId());
	}

	@Override
	public HomeType createHomeType(String typeName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return homeTypeService.createHomeType(typeName, userContext.getUser().getUserId());
	}

	@Override
	public HomeType deleteHomeType(long homeTypeId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return homeTypeService.deleteHomeType(homeTypeId);
	}

	@Override
	public HomeType findById(long homeTypeId) {
		return homeTypeService.findById(homeTypeId);
	}

}
