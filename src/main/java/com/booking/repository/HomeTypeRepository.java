/**
 * 
 */
package com.booking.repository;

import com.booking.model.HomeType;

/**
 * @author ddung
 *
 */
public interface HomeTypeRepository {
	public HomeType findById(long homeTypeId);

	public HomeType updateHomeType(HomeType homeType);

	public HomeType createHomeType(HomeType homeType);

	public HomeType deleteHomeType(long homeTypeId);
}
