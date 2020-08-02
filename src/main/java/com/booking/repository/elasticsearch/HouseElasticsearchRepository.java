/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.House;

/**
 * @author ddung
 *
 */
@Repository
public interface HouseElasticsearchRepository extends ElasticsearchRepository<House, Long> {

}
