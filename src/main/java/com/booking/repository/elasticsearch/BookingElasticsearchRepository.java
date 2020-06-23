/**
 * 
 */
package com.booking.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.booking.model.Booking;

/**
 * @author ddung
 *
 */
@Repository
public interface BookingElasticsearchRepository extends ElasticsearchRepository<Booking, Long> {

}
