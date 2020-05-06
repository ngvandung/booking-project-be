/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.VillageCategory;

/**
 * @author ddung
 *
 */
@Repository
public interface VillageCategoryElasticsearchRepository extends ElasticsearchRepository<VillageCategory, Long> {

}
