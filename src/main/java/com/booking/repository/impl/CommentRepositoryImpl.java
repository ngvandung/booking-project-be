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
import com.booking.repository.CommentRepository;
import com.booking.repository.elasticsearch.CommentElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class CommentRepositoryImpl implements CommentRepository {
	private static final Logger log = Logger.getLogger(CommentRepositoryImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CommentElasticsearchRepository commentElasticsearchRepository;

	@Override
	public Comment findById(long commentId) {
		Comment comment = null;
		try {
			Optional<Comment> optionalComment = commentElasticsearchRepository.findById(commentId);
			if (optionalComment.isPresent()) {
				comment = optionalComment.get();
			} else {
				Session session = sessionFactory.openSession();
				Transaction transaction = null;
				transaction = session.beginTransaction();
				comment = session.get(Comment.class, commentId);
				transaction.commit();
				session.close();
			}
			return comment;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Comment updateComment(Comment comment) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.update(comment);
		transaction.commit();
		session.close();
		return comment;
	}

	@Override
	public Comment createComment(Comment comment) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		session.save(comment);
		transaction.commit();
		session.close();
		return comment;
	}

	@Override
	public Comment deleteComment(long commentId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		transaction = session.beginTransaction();
		Comment comment = sessionFactory.getCurrentSession().get(Comment.class, commentId);
		session.delete(comment);
		transaction.commit();
		session.close();
		return comment;
	}

	@Override
	public List<Comment> findByClassName_ClassPK(String className, long classPK) {
		List<Comment> comments = new ArrayList<Comment>();
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			if (session != null) {
				// start a transaction
				transaction = session.beginTransaction();

				// get an student object
				String hql = " FROM Comment S WHERE S.className = :className AND S.classPK = :classPK ";
				Query query = session.createQuery(hql);
				query.setParameter("className", className);
				query.setParameter("classPK", classPK);
				comments = (List<Comment>) query.getResultList();

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
		return comments;
	}

}
