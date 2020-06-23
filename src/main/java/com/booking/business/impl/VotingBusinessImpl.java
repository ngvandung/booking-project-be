/**
 * 
 */
package com.booking.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.VotingBusiness;
import com.booking.model.Booking;
import com.booking.model.Home;
import com.booking.model.Voting;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.BookingService;
import com.booking.service.VotingService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class VotingBusinessImpl implements VotingBusiness {

	@Autowired
	private VotingService votingService;
	@Autowired
	private BookingService bookingService;

	@Override
	public Voting findById(long votingId) {
		return votingService.findById(votingId);
	}

	@Override
	public List<Voting> findByClassName_ClassPK(String className, long classPK) {
		return votingService.findByClassName_ClassPK(className, classPK);
	}

	@Override
	public Voting updateVoting(long votingId, int star, long classPK, String className, UserContext userContext) {
		Voting voting = findById(votingId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, voting.getUserId())) {
			voting = votingService.updateVoting(votingId, star, classPK, className, userContext.getUser().getUserId());
		}
		return voting;
	}

	@Override
	public Voting createVoting(int star, long classPK, String className, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		long userId = userContext.getUser().getUserId();
		if (className.equals(Home.class.getName())) {
			List<Booking> bookings = bookingService.findBookings(className, classPK, null, null, null, userId);
			if (bookings != null && !bookings.isEmpty()) {
				return votingService.createVoting(star, classPK, className, userId);
			}
		}
		return null;
	}

	@Override
	public Voting deleteVoting(long votingId, UserContext userContext) {
		Voting voting = findById(votingId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, voting.getUserId())) {
			voting = votingService.deleteVoting(votingId);
		}
		return voting;
	}

}
