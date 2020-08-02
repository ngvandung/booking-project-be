/**
 * 
 */
package com.booking.business;

import com.booking.model.HouseType;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface HouseTypeBusiness {
	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end);

	public HouseType updateHouseType(long houseTypeId, String typeName, UserContext userContext);

	public HouseType createHouseType(String typeName, UserContext userContext);

	public HouseType deleteHouseType(long houseTypeId, UserContext userContext);

	public HouseType findById(long houseTypeId);
	
	public void indexing(UserContext userContext);
}
