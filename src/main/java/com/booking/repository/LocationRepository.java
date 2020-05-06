/**
 * 
 */
package com.booking.repository;

import com.booking.model.Location;

/**
 * @author ddung
 *
 */
public interface LocationRepository {
	public Location findById(long locationId);

	public Location updateLocation(Location location);

	public Location createLocation(Location location);

	public Location deleteLocation(long locationId);
}
