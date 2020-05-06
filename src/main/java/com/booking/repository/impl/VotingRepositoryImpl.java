/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Voting;
import com.booking.repository.VotingRepository;
import com.booking.repository.elasticsearch.VotingElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
public class VotingRepositoryImpl implements VotingRepository {
	private static final Logger log = Logger.getLogger(VotingRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private VotingElasticsearchRepository votingElasticsearchRepository;

	@Override
	public Voting findById(long votingId) {
		Voting voting = null;
		try {
			Optional<Voting> optionalVoting = votingElasticsearchRepository.findById(votingId);
			if (optionalVoting.isPresent()) {
				voting = optionalVoting.get();
			} else {
				voting = sessionFactory.getCurrentSession().get(Voting.class, votingId);
			}
			return voting;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Voting updateVoting(Voting voting) {
		try {
			sessionFactory.getCurrentSession().update(voting);
			return voting;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Voting createVoting(Voting voting) {
		try {
			sessionFactory.getCurrentSession().save(voting);
			return voting;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Voting deleteVoting(long votingId) {
		try {
			Voting voting = sessionFactory.getCurrentSession().get(Voting.class, votingId);
			sessionFactory.getCurrentSession().delete(voting);
			return voting;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
}
