/**
 * 
 */
package com.booking.repository;

import com.booking.model.Voting;

/**
 * @author ddung
 *
 */
public interface VotingRepository {
	public Voting findById(long votingId);

	public Voting updateVoting(Voting voting);

	public Voting createVoting(Voting voting);

	public Voting deleteVoting(long votingId);
}
