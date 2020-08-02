/**
 * 
 */
package com.booking.repository;

import java.util.List;
import java.util.Map;

import com.booking.model.House;

/**
 * @author ddung
 *
 */
public interface HouseRepository {
	public House findById(long houseId);

	public House updateHouse(House house);

	public House createHouse(House house);

	public House deleteHouse(long houseId);

	public List<House> findAll();

	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag);
}
