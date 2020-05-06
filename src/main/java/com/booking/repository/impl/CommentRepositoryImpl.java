/**
 * 
 */
package com.booking.repository.impl;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.model.Comment;
import com.booking.repository.CommentRepository;
import com.booking.repository.elasticsearch.CommentElasticsearchRepository;

/**
 * @author ddung
 *
 */
@Repository
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
				comment = sessionFactory.getCurrentSession().get(Comment.class, commentId);
			}
			return comment;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Comment updateComment(Comment comment) {
		try {
			sessionFactory.getCurrentSession().update(comment);
			return comment;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Comment createComment(Comment comment) {
		try {
			sessionFactory.getCurrentSession().save(comment);
			return comment;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@Override
	public Comment deleteComment(long commentId) {
		try {
			Comment comment = sessionFactory.getCurrentSession().get(Comment.class, commentId);
			sessionFactory.getCurrentSession().delete(comment);
			return comment;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

}
