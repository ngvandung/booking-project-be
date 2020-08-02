/**
 * 
 */
package com.booking.service;

import com.booking.model.HouseType;

/**
 * @author ddung
 *
 */
public interface HouseTypeService {

	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end);

	public HouseType findById(long houseTypeId);

	public HouseType updateHouseType(long houseTypeId, String typeName, long userId);

	public HouseType createHouseType(String typeName, long userId);

	public HouseType deleteHouseType(long houseTypeId);

	public void indexing();
}
