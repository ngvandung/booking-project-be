/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.Voting;

/**
 * @author ddung
 *
 */
@Repository
public interface VotingElasticsearchRepository extends ElasticsearchRepository<Voting, Long> {

}
