/**
 * 
 */
package com.booking.repository;

import com.booking.model.RentHome;

/**
 * @author ddung
 *
 */
public interface RentHomeRepository {
	public RentHome findById(long rentId);

	public RentHome updateRentHome(RentHome rentHome);

	public RentHome createRentHome(RentHome rentHome);

	public RentHome deleteRentHome(long rentId);
}
