/**
 * 
 */
package com.booking.repository;

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
}
