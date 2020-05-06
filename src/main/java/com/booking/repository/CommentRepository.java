/**
 * 
 */
package com.booking.repository;

import com.booking.model.Comment;

/**
 * @author ddung
 *
 */
public interface CommentRepository {
	public Comment findById(long commentId);

	public Comment updateComment(Comment comment);

	public Comment createComment(Comment comment);

	public Comment deleteComment(long commentId);
}
