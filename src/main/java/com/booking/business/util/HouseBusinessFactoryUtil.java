/**
 * 
 */
package com.booking.business.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.booking.business.HouseBusiness;
import com.booking.model.House;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HouseBusinessFactoryUtil {

	// Design pattern - Singleton
	private static HouseBusiness _houseBusiness;

	public static HouseBusiness getHouseBusiness() {

		if (_houseBusiness == null) {
			_houseBusiness = BeanUtil.getBean(HouseBusiness.class);
		}
		return _houseBusiness;
	} // ============================

	public static House findById(long houseId) {
		return getHouseBusiness().findById(houseId);
	}

	public static House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String extensionCategoryDetailIds, String description, int isActive, long ownerHouseId,
			UserContext userContext) {
		return getHouseBusiness().updateHouse(houseId, name, houseTypeId, typeName, stateId, stateName, cityId,
				cityName, districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom,
				bathroom, maxGuest, extensionCategoryDetailIds, description, isActive, ownerHouseId, userContext);
	}

	public static House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest,
			String extensionCategoryDetailIds, String description, long ownerHouseId, UserContext userContext) {
		return getHouseBusiness().createHouse(name, houseTypeId, typeName, stateId, stateName, cityId, cityName,
				districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom, bathroom,
				maxGuest, extensionCategoryDetailIds, description, ownerHouseId, userContext);
	}

	public static House deleteHouse(long houseId, UserContext userContext) {
		return getHouseBusiness().deleteHouse(houseId, userContext);
	}

	public static House actionHouse(long houseId, int status, UserContext userContext) {
		return getHouseBusiness().actionHouse(houseId, status, userContext);
	}

	public static void indexing(UserContext userContext) {
		getHouseBusiness().indexing(userContext);
	}

	public static List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag, UserContext userContext) {
		return getHouseBusiness().findMyHouses(ownerHouseId, flag, userContext);
	}

	public static List<House> recommenderByKMean(long houseId) throws IOException {
		return getHouseBusiness().recommenderByKMean(houseId);
	}

	public static List<House> recommenderByCollaborativeFiltering(long userId) throws IOException {
		return getHouseBusiness().recommenderByCollaborativeFiltering(userId);
	}
}
