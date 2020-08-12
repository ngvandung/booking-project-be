/**
 * 
 */
package com.booking.business.util;

import com.booking.business.HouseTypeBusiness;
import com.booking.model.HouseType;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HouseTypeBusinessFactoryUtil {
	// Design pattern - Singleton
	private static HouseTypeBusiness _houseTypeBusiness;

	public static HouseTypeBusiness getHouseTypeBusiness() {

		if (_houseTypeBusiness == null) {
			_houseTypeBusiness = BeanUtil.getBean(HouseTypeBusiness.class);
		}
		return _houseTypeBusiness;
	}
	// ============================

	public static Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end) {
		return getHouseTypeBusiness().getHouseTypes(typeName, start, end);
	}

	public static HouseType updateHouseType(long houseTypeId, String typeName, UserContext userContext) {
		return getHouseTypeBusiness().updateHouseType(houseTypeId, typeName, userContext);
	}

	public static HouseType createHouseType(String typeName, UserContext userContext) {
		return getHouseTypeBusiness().createHouseType(typeName, userContext);
	}

	public static HouseType deleteHouseType(long houseTypeId, UserContext userContext) {
		return getHouseTypeBusiness().deleteHouseType(houseTypeId, userContext);
	}

	public static HouseType findById(long houseTypeId) {
		return getHouseTypeBusiness().findById(houseTypeId);
	}

	public static void indexing(UserContext userContext) {
		getHouseTypeBusiness().indexing(userContext);
	}
}
