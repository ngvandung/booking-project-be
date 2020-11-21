/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HouseBusiness;
import com.booking.exception.ForbiddenException;
import com.booking.model.House;
import com.booking.model.User;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HouseService;
import com.booking.service.UserService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HouseBusinessImpl implements HouseBusiness {

	@Autowired
	private HouseService houseService;
	@Autowired
	private UserService userService;

	@Override
	public House findById(long houseId) {
		return houseService.findById(houseId);
	}

	@Override
	public House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String extensionCategoryDetailIds, String description, int isActive, long ownerHouseId,
			UserContext userContext) {

		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}

		House house = houseService.findById(houseId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, house.getUserId())) {
			return houseService.updateHouse(houseId, name, houseTypeId, typeName, stateId, stateName, cityId, cityName,
					districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom,
					bathroom, maxGuest, extensionCategoryDetailIds, description, isActive, ownerHouseId,
					userContext.getUser().getUserId());
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest,
			String extensionCategoryDetailIds, String description, long ownerHouseId, UserContext userContext) {

		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}
		PermissionCheckerFactoryUtil.checkHost(userContext);

		return houseService.createHouse(name, houseTypeId, typeName, stateId, stateName, cityId, cityName, districtId,
				districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom, bathroom, maxGuest,
				extensionCategoryDetailIds, description, ownerHouseId, userContext.getUser().getUserId());
	}

	@Override
	public House deleteHouse(long houseId, UserContext userContext) {
		User user = userService.findByUserId(userContext.getUser().getUserId());
		if (user.getIsEnabled() != 1) {
			throw new ForbiddenException();
		}
		House house = houseService.findById(houseId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, house.getUserId())) {
			return houseService.deleteHouse(houseId);
		} else {
			throw new ForbiddenException();
		}
	}

	@Override
	public House actionHouse(long houseId, int status, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkManager(userContext);

		House house = findById(houseId);
		if (house != null) {
			house.setIsActive(status);
			house.setModifiedDate(new Date());

			house = houseService.updateHouse(house);
		}
		return house;
	}

	@Override
	public void indexing(UserContext userContext) {
		houseService.indexing();
	}

	@Override
	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag, UserContext userContext) {
		if (PermissionCheckerFactoryUtil.isOwner(userContext, ownerHouseId)) {
			return houseService.findMyHouses(ownerHouseId, flag);
		} else {
			throw new ForbiddenException();
		}
	}

}
