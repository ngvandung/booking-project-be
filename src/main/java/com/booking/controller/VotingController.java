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

import com.booking.business.util.VotingBusinessFactoryUtil;
import com.booking.model.Voting;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class VotingController {
	@RequestMapping(value = "/voting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Voting createvoting(HttpServletRequest request, HttpSession session, @RequestBody Voting voting) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return VotingBusinessFactoryUtil.createVoting(voting.getStar(), voting.getClassPK(), voting.getClassName(),
				userContext);
	}

	@RequestMapping(value = "/voting", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Voting updatevoting(HttpServletRequest request, HttpSession session, @RequestBody Voting voting) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return VotingBusinessFactoryUtil.updateVoting(voting.getVotingId(), voting.getStar(), voting.getClassPK(),
				voting.getClassName(), userContext);
	}

	@RequestMapping(value = "/voting/{votingId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Voting deletevoting(HttpServletRequest request, HttpSession session,
			@PathVariable("votingId") long votingId) {
		UserContext userContext = BeanUtil.getBean(UserContext.class);
		userContext = (UserContext) session.getAttribute("userContext");

		return VotingBusinessFactoryUtil.deleteVoting(votingId, userContext);
	}
}
