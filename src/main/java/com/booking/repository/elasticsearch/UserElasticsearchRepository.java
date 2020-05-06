/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.User;

/**
 * @author ddung
 *
 */
@Repository
public interface UserElasticsearchRepository extends ElasticsearchRepository<User, Long>{

}
