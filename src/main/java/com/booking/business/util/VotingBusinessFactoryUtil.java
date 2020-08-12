/**
 * 
 */
package com.booking.business.util;

import java.util.List;

import com.booking.business.VotingBusiness;
import com.booking.model.Voting;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class VotingBusinessFactoryUtil {
	// Design pattern - Singleton
	private static VotingBusiness _votingBusiness;

	public static VotingBusiness getVotingBusiness() {

		if (_votingBusiness == null) {
			_votingBusiness = BeanUtil.getBean(VotingBusiness.class);
		}
		return _votingBusiness;
	} // ============================

	public static Voting findById(long votingId) {
		return getVotingBusiness().findById(votingId);
	}

	public static List<Voting> findByClassName_ClassPK(String className, long classPK) {
		return getVotingBusiness().findByClassName_ClassPK(className, classPK);
	}

	public static Voting updateVoting(long votingId, int star, long classPK, String className,
			UserContext userContext) {
		return getVotingBusiness().updateVoting(votingId, star, classPK, className, userContext);
	}

	public static Voting createVoting(int star, long classPK, String className, UserContext userContext) {
		return getVotingBusiness().createVoting(star, classPK, className, userContext);
	}

	public static Voting deleteVoting(long votingId, UserContext userContext) {
		return getVotingBusiness().deleteVoting(votingId, userContext);
	}
}
