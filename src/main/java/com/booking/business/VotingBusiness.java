/**
 * 
 */
package com.booking.business;

import java.util.List;

import com.booking.model.Voting;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface VotingBusiness {
	public Voting findById(long votingId);

	public List<Voting> findByClassName_ClassPK(String className, long classPK);

	public Voting updateVoting(long votingId, int star, long classPK, String className, UserContext userContext);

	public Voting createVoting(int star, long classPK, String className, UserContext userContext);

	public Voting deleteVoting(long votingId, UserContext userContext);
}
