/**
 * 
 */
package com.booking.business.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.CommentBusiness;
import com.booking.exception.ForbiddenException;
import com.booking.model.Booking;
import com.booking.model.Comment;
import com.booking.model.Home;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.BookingService;
import com.booking.service.CommentService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class CommentBusinessImpl implements CommentBusiness {

	@Autowired
	private CommentService commentService;
	@Autowired
	private BookingService bookingService;

	@Override
	public Comment findById(long commentId) {
		return commentService.findById(commentId);
	}

	@Override
	public Comment updateComment(long commentId, String content, long classPK, String className,
			UserContext userContext) {
		Comment comment = findById(commentId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, comment.getUserId())) {
			comment = commentService.updateComment(commentId, content, classPK, className,
					userContext.getUser().getUserId());
		} else {
			throw new ForbiddenException();
		}
		return comment;
	}

	@Override
	public Comment createComment(String content, long classPK, String className, UserContext userContext) {
		PermissionCheckerFactoryUtil.checkAuthentication(userContext);

		long userId = userContext.getUser().getUserId();
		if (className.equals(Home.class.getName())) {
			List<Booking> bookings = bookingService.findBookings(className, classPK, null, null, null, userId);
			if (bookings != null && !bookings.isEmpty()) {
				return commentService.createComment(content, classPK, className, userId);
			} else {
				throw new ForbiddenException();
			}
		}
		return null;
	}

	@Override
	public Comment deleteComment(long commentId, UserContext userContext) {
		Comment comment = findById(commentId);
		if (PermissionCheckerFactoryUtil.isOwner(userContext, comment.getUserId())) {
			comment = commentService.deleteComment(commentId);
		} else {
			throw new ForbiddenException();
		}
		return comment;
	}

	@Override
	public List<Comment> findByClassName_ClassPK(String className, long classPK) {
		return commentService.findByClassName_ClassPK(className, classPK);
	}

}
