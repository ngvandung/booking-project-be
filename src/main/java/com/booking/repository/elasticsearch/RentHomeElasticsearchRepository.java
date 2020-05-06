/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.RentHome;

/**
 * @author ddung
 *
 */
@Repository
public interface RentHomeElasticsearchRepository extends ElasticsearchRepository<RentHome, Long> {

}
