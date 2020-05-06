/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.Comment;

/**
 * @author ddung
 *
 */
@Repository
public interface CommentElasticsearchRepository extends ElasticsearchRepository<Comment, Long> {

}
