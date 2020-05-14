/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Voting;
import com.booking.repository.VotingRepository;
import com.booking.repository.elasticsearch.VotingElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
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
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				voting = session.get(Voting.class, votingId);
				transaction.commit();
				session.close();
			}
			return voting;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Voting updateVoting(Voting voting) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(voting);
		transaction.commit();
		session.close();
		return voting;
	}

	@Override
	public Voting createVoting(Voting voting) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(voting);
		transaction.commit();
		session.close();
		return voting;
	}

	@Override
	public Voting deleteVoting(long votingId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Voting voting = session.get(Voting.class, votingId);
		session.delete(voting);
		transaction.commit();
		session.close();
		return voting;
	}
}
