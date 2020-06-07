/**
 * 
 */
package com.booking.business.util;

import com.booking.business.HomeBusiness;
import com.booking.model.Home;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HomeBusinessFactoryUtil {

	// Design pattern - Singleton
	public static HomeBusiness _homeBusiness;

	public static HomeBusiness getHomeBusiness() {

		if (_homeBusiness == null) {
			_homeBusiness = BeanUtil.getBean(HomeBusiness.class);
		}
		return _homeBusiness;
	} // ============================

	public static Home findById(long homeId) {
		return getHomeBusiness().findById(homeId);
	}

	public static Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName,
			long stateId, String stateName, long cityId, String cityName, long districtId, String districtName,
			long villageId, String villageName, String linkGoogleMap, double price, int countBedroom,
			int countLivingroom, int countBathroom, int countPeople, String description, int isActive, long ownerHomeId,
			UserContext userContext) {
		return getHomeBusiness().updateHome(homeId, name, categoryId, homeTypeId, typeName, stateId, stateName, cityId,
				cityName, districtId, districtName, villageId, villageName, linkGoogleMap, price, countBedroom,
				countLivingroom, countBathroom, countPeople, description, isActive, ownerHomeId, userContext);
	}

	public static Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int countBedroom, int countLivingroom,
			int countBathroom, int countPeople, String description, long ownerHomeId, UserContext userContext) {
		return getHomeBusiness().createHome(name, categoryId, homeTypeId, typeName, stateId, stateName, cityId,
				cityName, districtId, districtName, villageId, villageName, linkGoogleMap, price, countBedroom,
				countLivingroom, countBathroom, countPeople, description, ownerHomeId, userContext);
	}

	public static Home deleteHome(long homeId, UserContext userContext) {
		return getHomeBusiness().deleteHome(homeId, userContext);
	}
	
	public static Home activeHome(long homeId, UserContext userContext) {
		return getHomeBusiness().activeHome(homeId, userContext);
	}
}
