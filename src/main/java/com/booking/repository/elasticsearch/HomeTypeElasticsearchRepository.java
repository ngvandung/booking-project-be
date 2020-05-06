/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.HomeType;

/**
 * @author ddung
 *
 */
@Repository
public interface HomeTypeElasticsearchRepository extends ElasticsearchRepository<HomeType, Long> {

}
