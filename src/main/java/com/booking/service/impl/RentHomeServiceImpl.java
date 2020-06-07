/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.model.RentHome;
import com.booking.repository.RentHomeRepository;
import com.booking.service.CounterService;
import com.booking.service.RentHomeService;

/**
 * @author ddung
 *
 */
@Service
public class RentHomeServiceImpl implements RentHomeService {

	private static final Logger log = Logger.getLogger(RentHomeServiceImpl.class);

	@Autowired
	private RentHomeRepository rentHomeRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public RentHome findById(long rentId) {
		return rentHomeRepository.findById(rentId);
	}

	@Override
	public RentHome updateRentHome(long rentId, int rentPeople, Date fromDate, Date toDate, long homeId,
			Long rentUserId, double totalAmount, long userId) {
		RentHome rentHome = rentHomeRepository.findById(rentId);

		if (rentHome != null) {
			rentHome.setRentPeople(rentPeople);
			rentHome.setRentUserId(rentUserId);
			rentHome.setToDate(toDate);
			rentHome.setFromDate(fromDate);
			rentHome.setUserId(userId);
			rentHome.setTotalAmount(totalAmount);
			rentHome.setModifiedDate(new Date());
			rentHome.setHomeId(homeId);

			rentHome = rentHomeRepository.updateRentHome(rentHome);
			if (rentHome != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(rentHome.getRentId()))
						.withObject(rentHome).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}
		return rentHome;
	}

	@Override
	public RentHome createRentHome(int rentPeople, Date fromDate, Date toDate, long homeId, Long rentUserId,
			double totalAmount, long userId) {
		RentHome rentHome = new RentHome();

		long rentId = counterService.increment(RentHome.class.getName());
		rentHome.setRentId(rentId);
		rentHome.setRentPeople(rentPeople);
		rentHome.setRentUserId(rentUserId);
		rentHome.setToDate(toDate);
		rentHome.setFromDate(fromDate);
		rentHome.setUserId(userId);
		rentHome.setTotalAmount(totalAmount);
		rentHome.setModifiedDate(new Date());
		rentHome.setHomeId(homeId);
		rentHome.setCreateDate(new Date());

		rentHome = rentHomeRepository.createRentHome(rentHome);
		if (rentHome != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(rentHome.getRentId()))
					.withObject(rentHome).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return rentHome;

	}

	@Override
	public RentHome deleteRentHome(long rentId) {
		RentHome rentHome = rentHomeRepository.deleteRentHome(rentId);
		if (rentHome != null) {
			String documentId = elasticsearchOperations.delete(RentHome.class, String.valueOf(rentId));
			log.info("documentId: " + documentId);
		}
		return rentHome;
	}

}
