/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.Comment;

/**
 * @author ddung
 *
 */
public interface CommentRepository {
	public Comment findById(long commentId);

	public List<Comment> findByClassName_ClassPK(String className, long classPK);

	public Comment updateComment(Comment comment);

	public Comment createComment(Comment comment);

	public Comment deleteComment(long commentId);
}
