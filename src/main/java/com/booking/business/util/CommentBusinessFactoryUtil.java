/**
 * 
 */
package com.booking.business.util;

import java.util.List;

import com.booking.business.CommentBusiness;
import com.booking.model.Comment;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class CommentBusinessFactoryUtil {
	// Design pattern - Singleton
	public static CommentBusiness _commentBusiness;

	public static CommentBusiness getCommentBusiness() {

		if (_commentBusiness == null) {
			_commentBusiness = BeanUtil.getBean(CommentBusiness.class);
		}
		return _commentBusiness;
	} // ============================

	public static Comment findById(long commentId) {
		return getCommentBusiness().findById(commentId);
	}

	public static List<Comment> findByClassName_ClassPK(String className, long classPK) {
		return getCommentBusiness().findByClassName_ClassPK(className, classPK);
	}

	public static Comment updateComment(long commentId, String content, long classPK, String className,
			UserContext userContext) {
		return getCommentBusiness().updateComment(commentId, content, classPK, className, userContext);
	}

	public static Comment createComment(String content, long classPK, String className, UserContext userContext) {
		return getCommentBusiness().createComment(content, classPK, className, userContext);
	}

	public static Comment deleteComment(long commentId, UserContext userContext) {
		return getCommentBusiness().deleteComment(commentId, userContext);
	}
}
