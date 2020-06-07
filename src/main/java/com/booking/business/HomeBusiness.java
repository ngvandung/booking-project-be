/**
 * 
 */
package com.booking.business;

import com.booking.model.Home;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface HomeBusiness {
	public Home findById(long homeId);

	public Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int countBedroom, int countLivingroom,
			int countBathroom, int countPeople, String description, int isActive, long ownerHomeId,
			UserContext userContext);

	public Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int countBedroom, int countLivingroom,
			int countBathroom, int countPeople, String description, long ownerHomeId, UserContext userContext);

	public Home deleteHome(long homeId, UserContext userContext);
	
	public Home activeHome(long homeId, UserContext userContext);
}
