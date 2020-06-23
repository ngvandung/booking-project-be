/**
 * 
 */
package com.booking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.business.util.CommentBusinessFactoryUtil;
import com.booking.model.Comment;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class CommentController {
	@RequestMapping(value = "/comment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Comment createComment(HttpServletRequest request, HttpSession session, @RequestBody Comment comment) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return CommentBusinessFactoryUtil.createComment(comment.getContent(), comment.getClassPK(),
				comment.getClassName(), userContext);
	}

	@RequestMapping(value = "/comment", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Comment updateComment(HttpServletRequest request, HttpSession session, @RequestBody Comment comment) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return CommentBusinessFactoryUtil.updateComment(comment.getCommentId(), comment.getContent(),
				comment.getClassPK(), comment.getClassName(), userContext);
	}

	@RequestMapping(value = "/comment/{commentId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Comment deleteComment(HttpServletRequest request, HttpSession session,
			@PathVariable("commentId") long commentId) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return CommentBusinessFactoryUtil.deleteComment(commentId, userContext);
	}
}
