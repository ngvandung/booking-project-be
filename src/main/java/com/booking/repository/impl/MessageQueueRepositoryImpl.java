/**
 * 
 */
package com.booking.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.model.MessageQueue;
import com.booking.repository.MessageQueueRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class MessageQueueRepositoryImpl implements MessageQueueRepository {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public MessageQueue findById(long messageQueueId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		MessageQueue messageQueue = session.get(MessageQueue.class, messageQueueId);
		transaction.commit();
		session.close();
		return messageQueue;
	}

	@Override
	public List<MessageQueue> findMessageQueues(Integer state) {
		List<MessageQueue> result = new ArrayList<MessageQueue>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				transaction = session.beginTransaction();

				StringBuilder hql = new StringBuilder();
				hql.append("FROM MessageQueue M WHERE 1 = 1");
				if (state != null) {
					hql.append("AND M.state = :state");
				}

				Query query = session.createQuery(hql.toString());

				if (state != null) {
					query.setParameter("state", state);
				}

				result = (List<MessageQueue>) query.getResultList();

				transaction.commit();
			}
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			// log.error(e);
		}
		session.close();

		return result;
	}

	@Override
	public MessageQueue createMessageQueue(MessageQueue messageQueue) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(messageQueue);
		transaction.commit();
		session.close();
		return messageQueue;
	}

	@Override
	public MessageQueue updateMessageQueue(MessageQueue messageQueue) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(messageQueue);
		transaction.commit();
		session.close();
		return messageQueue;
	}

}
