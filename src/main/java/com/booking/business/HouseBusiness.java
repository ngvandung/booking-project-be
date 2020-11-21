/**
 * 
 */
package com.booking.business;

import java.util.List;
import java.util.Map;

import com.booking.model.House;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface HouseBusiness {
	public House findById(long houseId);

	public House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String extensionCategoryDetailIds, String description, int isActive, long ownerHouseId,
			UserContext userContext);

	public House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest,
			String extensionCategoryDetailIds, String description, long ownerHouseId, UserContext userContext);

	public House deleteHouse(long houseId, UserContext userContext);

	public House actionHouse(long houseId, int status, UserContext userContext);

	public void indexing(UserContext userContext);

	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag, UserContext userContext);

}
