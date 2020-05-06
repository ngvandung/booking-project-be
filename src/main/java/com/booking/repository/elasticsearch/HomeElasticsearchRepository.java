/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.Home;

/**
 * @author ddung
 *
 */
@Repository
public interface HomeElasticsearchRepository extends ElasticsearchRepository<Home, Long> {

}
