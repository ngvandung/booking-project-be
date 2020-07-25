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

import com.booking.model.Voting;
import com.booking.repository.VotingRepository;
import com.booking.service.CounterService;
import com.booking.service.VotingService;

/**
 * @author ddung
 *
 */
@Service
public class VotingServiceImpl implements VotingService {

	private static final Logger log = Logger.getLogger(VotingServiceImpl.class);
	@Autowired
	private VotingRepository votingRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Voting findById(long votingId) {
		return votingRepository.findById(votingId);
	}

	@Override
	public Voting updateVoting(long votingId, int star, long classPK, String className, long userId) {
		Voting voting = findById(votingId);
		if (voting != null) {
			voting.setClassName(className);
			voting.setClassPK(classPK);
			voting.setStar(star);
			voting.setUserId(userId);
			voting.setModifiedDate(new Date());

			voting = votingRepository.updateVoting(voting);
			if (voting != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(voting.getVotingId()))
						.withObject(voting).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}
		return voting;
	}

	@Override
	public Voting createVoting(int star, long classPK, String className, long userId) {
		Voting voting = new Voting();

		long votingId = counterService.increment(Voting.class.getName());

		voting.setVotingId(votingId);
		voting.setClassName(className);
		voting.setClassPK(classPK);
		voting.setStar(star);
		voting.setUserId(userId);
		voting.setModifiedDate(new Date());
		voting.setCreateDate(new Date());

		voting = votingRepository.createVoting(voting);
		if (voting != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(voting.getVotingId()))
					.withObject(voting).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return voting;
	}

	@Override
	public Voting deleteVoting(long votingId) {
		Voting voting = findById(votingId);
		voting = votingRepository.deleteVoting(votingId);
		return voting;
	}

	@Override
	public List<Voting> findByClassName_ClassPK(String className, long classPK) {
		return votingRepository.findByClassName_ClassPK(className, classPK);
	}

}
