/**
 * 
 */
package com.booking.service;

import com.booking.model.Home;

/**
 * @author ddung
 *
 */
public interface HomeService {
	public Home findById(long homeId);

	public Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String description, int isActive, long ownerHomeId, long userId);

	public Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String description, long ownerHomeId, long userId);

	public Home deleteHome(long homeId);

	public Home updateHome(Home home);
}
