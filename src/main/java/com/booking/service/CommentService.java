/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.Comment;

/**
 * @author ddung
 *
 */
public interface CommentService {
	public Comment findById(long commentId);

	public List<Comment> findByClassName_ClassPK(String className, long classPK);

	public Comment updateComment(long commentId, String content, long classPK, String className, long userId);

	public Comment createComment(String content, long classPK, String className, long userId);

	public Comment deleteComment(long commentId);
}
