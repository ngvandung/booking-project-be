/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.model.Comment;
import com.booking.repository.CommentRepository;
import com.booking.service.CommentService;
import com.booking.service.CounterService;

/**
 * @author ddung
 *
 */
@Service
public class CommentServiceImpl implements CommentService {
	private static final Logger log = Logger.getLogger(CommentServiceImpl.class);
	@Autowired
	private CommentRepository commentRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private CounterService counterService;

	@Override
	public Comment findById(long commentId) {
		return commentRepository.findById(commentId);
	}

	@Override
	public Comment updateComment(long commentId, String content, long classPK, String className, long userId) {
		Comment comment = findById(commentId);
		if (comment != null) {
			comment.setContent(content);
			comment.setClassName(className);
			comment.setClassPK(classPK);
			comment.setUserId(userId);
			comment.setModifiedDate(new Date());

			comment = commentRepository.updateComment(comment);
			if (comment != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(comment.getCommentId()))
						.withObject(comment).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}
		return comment;

	}

	@Override
	public Comment createComment(String content, long classPK, String className, long userId) {
		Comment comment = new Comment();

		long commentId = counterService.increment(Comment.class.getName());

		comment.setCommentId(commentId);
		comment.setContent(content);
		comment.setClassName(className);
		comment.setClassPK(classPK);
		comment.setUserId(userId);
		comment.setModifiedDate(new Date());
		comment.setCreateDate(new Date());

		comment = commentRepository.createComment(comment);
		if (comment != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(comment.getCommentId()))
					.withObject(comment).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return comment;
	}

	@Override
	public Comment deleteComment(long commentId) {
		Comment comment = findById(commentId);
		comment = commentRepository.deleteComment(commentId);
		return comment;
	}

	@Override
	public List<Comment> findByClassName_ClassPK(String className, long classPK) {
		return commentRepository.findByClassName_ClassPK(className, classPK);
	}

}
