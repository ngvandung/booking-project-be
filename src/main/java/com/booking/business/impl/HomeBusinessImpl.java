/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HomeBusiness;
import com.booking.constant.HomeConstant;
import com.booking.exception.ForbiddenException;
import com.booking.model.Home;
import com.booking.model.User;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HomeService;
import com.booking.service.UserService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HomeBusinessImpl implements HomeBusiness {

	@Autowired
	private HomeService homeService;
	@Autowired
	private UserService userService;

	@Override
	public Home findById(long homeId) {
		return homeService.findById(homeId);
	}

	@Override
	public Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, int isWifi, int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste,
			int isSoap, int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, int isActive, long ownerHomeId, UserContext userContext) {

		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}

		Home home = homeService.findById(homeId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, home.getUserId())) {
			return homeService.updateHome(homeId, name, categoryId, homeTypeId, typeName, stateId, stateName, cityId,
					cityName, districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom,
					livingroom, bathroom, maxGuest, isWifi, isOven, isAirConditioning, isShampoo, isTowels,
					isToothpaste, isSoap, isHairDryer, isMicroWave, isFridge, isBalcony, isWindows, isSmartTv,
					isExtraMattress, description, isActive, ownerHomeId, userContext.getUser().getUserId());
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, int isWifi, int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste,
			int isSoap, int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, long ownerHomeId, UserContext userContext) {

		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}
		PermissionCheckerFactoryUtil.checkHost(userContext);

		return homeService.createHome(name, categoryId, homeTypeId, typeName, stateId, stateName, cityId, cityName,
				districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom, bathroom,
				maxGuest, isWifi, isOven, isAirConditioning, isShampoo, isTowels, isToothpaste, isSoap, isHairDryer,
				isMicroWave, isFridge, isBalcony, isWindows, isSmartTv, isExtraMattress, description, ownerHomeId,
				userContext.getUser().getUserId());
	}

	@Override
	public Home deleteHome(long homeId, UserContext userContext) {
		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}
		Home home = homeService.findById(homeId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, home.getUserId())) {
			return homeService.deleteHome(homeId);
		} else {
			throw new ForbiddenException();
		}
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

	@Override
	public void indexing(UserContext userContext) {
		homeService.indexing();
	}

}
