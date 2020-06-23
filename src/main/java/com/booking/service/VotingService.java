/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.Voting;

/**
 * @author ddung
 *
 */
public interface VotingService {
	public Voting findById(long votingId);

	public List<Voting> findByClassName_ClassPK(String className, long classPK);

	public Voting updateVoting(long votingId, int star, long classPK, String className, long userId);

	public Voting createVoting(int star, long classPK, String className, long userId);

	public Voting deleteVoting(long votingId);
}
