/**
 * 
 */
package com.booking.repository;

import java.util.List;
import java.util.Map;

import com.booking.model.Home;

/**
 * @author ddung
 *
 */
public interface HomeRepository {
	public Home findById(long homeId);

	public Home updateHome(Home home);

	public Home createHome(Home home);

	public Home deleteHome(long homeId);

	public List<Home> findAll();

	public List<Map<String, Object>> findMyHomes(Long ownerHomeId, String flag);
}
