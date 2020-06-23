/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.Voting;

/**
 * @author ddung
 *
 */
public interface VotingRepository {
	public Voting findById(long votingId);

	public List<Voting> findByClassName_ClassPK(String className, long classPK);

	public Voting updateVoting(Voting voting);

	public Voting createVoting(Voting voting);

	public Voting deleteVoting(long votingId);
}
