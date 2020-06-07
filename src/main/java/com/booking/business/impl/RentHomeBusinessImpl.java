/**
 * 
 */
package com.booking.business.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.RentHomeBusiness;
import com.booking.model.Home;
import com.booking.model.RentHome;
import com.booking.service.HomeService;
import com.booking.service.RentHomeService;
import com.booking.util.RentUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class RentHomeBusinessImpl implements RentHomeBusiness {

	@Autowired
	private RentHomeService rentHomeService;
	@Autowired
	private HomeService homeService;

	@Override
	public RentHome findById(long rentId) {
		return rentHomeService.findById(rentId);
	}

	@Override
	public RentHome updateRentHome(long rentId, int rentPeople, Date fromDate, Date toDate, long homeId,
			Long rentUserId, UserContext userContext) {
		double totalAmount = 0;
		Home home = homeService.findById(homeId);
		if (home != null) {
			double rentTime = RentUtil.calculateRentTime(fromDate, toDate);
			double price = home.getPrice();
			totalAmount = rentTime * price;
		}
		return rentHomeService.updateRentHome(rentId, rentPeople, fromDate, toDate, homeId, rentUserId, totalAmount,
				userContext.getUser().getUserId());
	}

	@Override
	public RentHome createRentHome(int rentPeople, Date fromDate, Date toDate, long homeId, Long rentUserId,
			UserContext userContext) {
		double totalAmount = 0;
		Home home = homeService.findById(homeId);
		if (home != null) {
			double rentTime = RentUtil.calculateRentTime(fromDate, toDate);
			double price = home.getPrice();
			totalAmount = rentTime * price;
		}
		return rentHomeService.createRentHome(rentPeople, fromDate, toDate, homeId, rentUserId, totalAmount,
				userContext.getUser().getUserId());
	}

	@Override
	public RentHome deleteRentHome(long rentId, UserContext userContext) {
		return rentHomeService.deleteRentHome(rentId);
	}

}
