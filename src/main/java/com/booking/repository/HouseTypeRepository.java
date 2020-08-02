/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.HouseType;

/**
 * @author ddung
 *
 */
public interface HouseTypeRepository {

	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end);

	public HouseType findById(long houseTypeId);

	public HouseType updateHouseType(HouseType houseType);

	public HouseType createHouseType(HouseType houseType);

	public HouseType deleteHouseType(long houseTypeId);
	
	public List<HouseType> findAll();
}
