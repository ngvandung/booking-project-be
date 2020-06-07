/**
 * 
 */
package com.booking.business.util;

import java.util.Date;

import com.booking.business.RentHomeBusiness;
import com.booking.model.RentHome;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class RentHomeBusinessFactoryUtil {
	// Design pattern - Singleton
	public static RentHomeBusiness _rentHomeBusiness;

	public static RentHomeBusiness getRentHomeBusiness() {

		if (_rentHomeBusiness == null) {
			_rentHomeBusiness = BeanUtil.getBean(RentHomeBusiness.class);
		}
		return _rentHomeBusiness;
	} // ============================

	public static RentHome findById(long rentId) {
		return getRentHomeBusiness().findById(rentId);
	}

	public static RentHome updateRentHome(long rentId, int rentPeople, Date fromDate, Date toDate, long homeId,
			Long rentUserId, UserContext userContext) {
		return getRentHomeBusiness().updateRentHome(rentId, rentPeople, fromDate, toDate, homeId, rentUserId,
				userContext);
	}

	public static RentHome createRentHome(int rentPeople, Date fromDate, Date toDate, long homeId, Long rentUserId,
			UserContext userContext) {
		return getRentHomeBusiness().createRentHome(rentPeople, fromDate, toDate, homeId, rentUserId, userContext);
	}

	public static RentHome deleteRentHome(long rentId, UserContext userContext) {
		return getRentHomeBusiness().deleteRentHome(rentId, userContext);
	}
}
