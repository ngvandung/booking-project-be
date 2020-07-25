/**
 * 
 */
package com.booking.business;

import java.util.List;
import java.util.Map;

import com.booking.model.Home;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface HomeBusiness {
	public Home findById(long homeId);

	public Home updateHome(long homeId, String name, long homeTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest, int isWifi,
			int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste, int isSoap,
			int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, int isActive, long ownerHomeId, UserContext userContext);

	public Home createHome(String name, long homeTypeId, String typeName, long stateId, String stateName, long cityId,
			String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest, int isWifi,
			int isOven, int isAirConditioning, int isShampoo, int isTowels, int isToothpaste, int isSoap,
			int isHairDryer, int isMicroWave, int isFridge, int isBalcony, int isWindows, int isSmartTv,
			int isExtraMattress, String description, long ownerHomeId, UserContext userContext);

	public Home deleteHome(long homeId, UserContext userContext);

	public Home actionHome(long homeId, int status, UserContext userContext);

	public void indexing(UserContext userContext);

	public List<Map<String, Object>> findMyHomes(Long ownerHomeId, String flag, UserContext userContext);

}
