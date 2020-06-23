/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.Comment;
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

	@Override
	public List<Voting> findByClassName_ClassPK(String className, long classPK) {
		List<Voting> votings = new ArrayList<Voting>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM Voting S WHERE S.className = :className AND S.classPK = :classPK ";
				Query query = session.createQuery(hql);
				query.setParameter("className", className);
				query.setParameter("classPK", classPK);
				votings = (List<Voting>) query.getResultList();

				// commit transaction
				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(e);
		}
		session.close();
		return votings;
	}
}
