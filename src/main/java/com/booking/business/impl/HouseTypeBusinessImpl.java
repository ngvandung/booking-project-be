/**
 * 
 */
package com.booking.business.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HouseTypeBusiness;
import com.booking.model.HouseType;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HouseTypeService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HouseTypeBusinessImpl implements HouseTypeBusiness {

	@Autowired
	private HouseTypeService houseTypeService;

	@Override
	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end) {
		return houseTypeService.getHouseTypes(typeName, start, end);
	}

	@Override
	public HouseType updateHouseType(long houseTypeId, String typeName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return houseTypeService.updateHouseType(houseTypeId, typeName, userContext.getUser().getUserId());
	}

	@Override
	public HouseType createHouseType(String typeName, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return houseTypeService.createHouseType(typeName, userContext.getUser().getUserId());
	}

	@Override
	public HouseType deleteHouseType(long houseTypeId, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		return houseTypeService.deleteHouseType(houseTypeId);
	}

	@Override
	public HouseType findById(long houseTypeId) {
		return houseTypeService.findById(houseTypeId);
	}

	@Override
	public void indexing(UserContext userContext) {
		houseTypeService.indexing();
	}

}
