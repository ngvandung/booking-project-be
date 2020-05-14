/**
 * 
 */
package com.booking.business.util;

import com.booking.business.HomeTypeBusiness;
import com.booking.model.HomeType;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HomeTypeBusinessFactoryUtil {
	// Design pattern - Singleton
	public static HomeTypeBusiness _homeTypeBusiness;

	public static HomeTypeBusiness getHomeTypeBusiness() {

		if (_homeTypeBusiness == null) {
			_homeTypeBusiness = BeanUtil.getBean(HomeTypeBusiness.class);
		}
		return _homeTypeBusiness;
	}
	// ============================

	public static Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end) {
		return getHomeTypeBusiness().getHomeTypes(typeName, start, end);
	}

	public static HomeType updateHomeType(long homeTypeId, String typeName, UserContext userContext) {
		return getHomeTypeBusiness().updateHomeType(homeTypeId, typeName, userContext);
	}

	public static HomeType createHomeType(String typeName, UserContext userContext) {
		return getHomeTypeBusiness().createHomeType(typeName, userContext);
	}

	public static HomeType deleteHomeType(long homeTypeId, UserContext userContext) {
		return getHomeTypeBusiness().deleteHomeType(homeTypeId, userContext);
	}

	public static HomeType findById(long homeTypeId) {
		return getHomeTypeBusiness().findById(homeTypeId);
	}
}
