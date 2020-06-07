/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HomeBusiness;
import com.booking.constant.HomeConstant;
import com.booking.model.Home;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HomeService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HomeBusinessImpl implements HomeBusiness {

	@Autowired
	private HomeService homeService;

	@Override
	public Home findById(long homeId) {
		return homeService.findById(homeId);
	}

	@Override
	public Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int countBedroom, int countLivingroom,
			int countBathroom, int countPeople, String description, int isActive, long ownerHomeId,
			UserContext userContext) {
		Home home = homeService.findById(homeId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, home.getUserId())) {
			return homeService.updateHome(homeId, name, categoryId, homeTypeId, typeName, stateId, stateName, cityId,
					cityName, districtId, districtName, villageId, villageName, linkGoogleMap, price, countBedroom,
					countLivingroom, countBathroom, countPeople, description, isActive, ownerHomeId,
					userContext.getUser().getUserId());
		}
		return null;
	}

	@Override
	public Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int countBedroom, int countLivingroom,
			int countBathroom, int countPeople, String description, long ownerHomeId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkHost(userContext);

		return homeService.createHome(name, categoryId, homeTypeId, typeName, stateId, stateName, cityId, cityName,
				districtId, districtName, villageId, villageName, linkGoogleMap, price, countBedroom, countLivingroom,
				countBathroom, countPeople, description, ownerHomeId, userContext.getUser().getUserId());
	}

	@Override
	public Home deleteHome(long homeId, UserContext userContext) {
		Home home = homeService.findById(homeId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, home.getUserId())) {
			return homeService.deleteHome(homeId);
		}
		return null;
	}

	@Override
	public Home activeHome(long homeId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkManager(userContext);

		Home home = findById(homeId);
		if (home != null) {
			home.setIsActive(HomeConstant.ACTIVE);
			home.setModifiedDate(new Date());

			home = homeService.updateHome(home);
		}
		return home;
	}

}
