/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.HouseType;

/**
 * @author ddung
 *
 */
@Repository
public interface HouseTypeElasticsearchRepository extends ElasticsearchRepository<HouseType, Long> {

}
