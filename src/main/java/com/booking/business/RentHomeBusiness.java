/**
 * 
 */
package com.booking.business;

import java.util.Date;

import com.booking.model.RentHome;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface RentHomeBusiness {
	public RentHome findById(long rentId);

	public RentHome updateRentHome(long rentId, int rentPeople, Date fromDate, Date toDate, long homeId,
			Long rentUserId, UserContext userContext);

	public RentHome createRentHome(int rentPeople, Date fromDate, Date toDate, long homeId, Long rentUserId,
			UserContext userContext);

	public RentHome deleteRentHome(long rentId, UserContext userContext);
}
