/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.Location;

/**
 * @author ddung
 *
 */
@Repository
public interface LocationElasticsearchRepository extends ElasticsearchRepository<Location, Long> {

}
