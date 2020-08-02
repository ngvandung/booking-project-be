/**
 * 
 */
package com.booking.service;

import java.util.List;
import java.util.Map;

import com.booking.model.House;

/**
 * @author ddung
 *
 */
public interface HouseService {
	public House findById(long houseId);

	public House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, int isWifi, int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste,
			int isSoap, int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, int isActive, long ownerHouseId, long userId);

	public House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest, int isWifi,
			int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste, int isSoap,
			int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, long ownerHouseId, long userId);

	public House deleteHouse(long houseId);

	public House updateHouse(House house);

	public void indexing();

	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag);
}
