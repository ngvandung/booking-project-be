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

import com.booking.model.HomeType;
import com.booking.repository.HomeTypeRepository;
import com.booking.service.CounterService;
import com.booking.service.HomeTypeService;

/**
 * @author ddung
 *
 */
@Service
public class HomeTypeServiceImpl implements HomeTypeService {

	private static final Logger log = Logger.getLogger(HomeTypeServiceImpl.class);

	@Autowired
	private HomeTypeRepository homeTypeRepository;

	@Resource
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private CounterService counterService;

	@Override
	public HomeType findById(long homeTypeId) {
		return homeTypeRepository.findById(homeTypeId);
	}

	@Override
	public HomeType updateHomeType(long homeTypeId, String typeName, long userId) {
		HomeType homeType = homeTypeRepository.findById(homeTypeId);

		if (homeType != null) {
			homeType.setTypeName(typeName);
			homeType.setModifiedDate(new Date());
			homeType.setUserId(userId);

			homeType = homeTypeRepository.updateHomeType(homeType);
			if (homeType != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(homeType.getHomeTypeId()))
						.withObject(homeType).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return homeType;
	}

	@Override
	public HomeType createHomeType(String typeName, long userId) {
		HomeType homeType = new HomeType();

		long homeTypeId = counterService.increment(HomeType.class.getName());

		homeType.setHomeTypeId(homeTypeId);
		homeType.setTypeName(typeName);
		homeType.setCreateDate(new Date());
		homeType.setModifiedDate(new Date());
		homeType.setUserId(userId);

		homeType = homeTypeRepository.createHomeType(homeType);
		if (homeType != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(homeType.getHomeTypeId()))
					.withObject(homeType).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return homeType;
	}

	@Override
	public HomeType deleteHomeType(long homeTypeId) {
		HomeType homeType = homeTypeRepository.deleteHomeType(homeTypeId);
		if (homeType != null) {
			String documentId = elasticsearchOperations.delete(HomeType.class, String.valueOf(homeTypeId));
			log.info("documentId: " + documentId);
		}
		return homeType;
	}

	@Override
	public Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end) {
		return homeTypeRepository.getHomeTypes(typeName, start, end);
	}

}
