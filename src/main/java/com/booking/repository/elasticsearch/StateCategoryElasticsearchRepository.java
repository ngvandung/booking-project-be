/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.StateCategory;

/**
 * @author ddung
 *
 */
@Repository
public interface StateCategoryElasticsearchRepository extends ElasticsearchRepository<StateCategory, Long> {

}
