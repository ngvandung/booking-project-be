/**
 * 
 */
package com.booking.service;

import java.util.List;
import java.util.Map;

import com.booking.model.Home;

/**
 * @author ddung
 *
 */
public interface HomeService {
	public Home findById(long homeId);

	public Home updateHome(long homeId, String name, long homeTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest, int isWifi,
			int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste, int isSoap,
			int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, int isActive, long ownerHomeId, long userId);

	public Home createHome(String name, long homeTypeId, String typeName, long stateId, String stateName, long cityId,
			String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest, int isWifi,
			int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste, int isSoap,
			int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, long ownerHomeId, long userId);

	public Home deleteHome(long homeId);

	public Home updateHome(Home home);

	public void indexing();

	public List<Map<String, Object>> findMyHomes(Long ownerHomeId, String flag);
}
