/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.model.HouseType;
import com.booking.repository.HouseTypeRepository;
import com.booking.service.CounterService;
import com.booking.service.HouseTypeService;

/**
 * @author ddung
 *
 */
@Service
public class HouseTypeServiceImpl implements HouseTypeService {

	private static final Logger log = Logger.getLogger(HouseTypeServiceImpl.class);

	@Autowired
	private HouseTypeRepository houseTypeRepository;

	@Resource
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private CounterService counterService;

	@Override
	public HouseType findById(long houseTypeId) {
		return houseTypeRepository.findById(houseTypeId);
	}

	@Override
	public HouseType updateHouseType(long houseTypeId, String typeName, long userId) {
		HouseType houseType = houseTypeRepository.findById(houseTypeId);

		if (houseType != null) {
			houseType.setTypeName(typeName);
			houseType.setModifiedDate(new Date());
			houseType.setUserId(userId);

			houseType = houseTypeRepository.updateHouseType(houseType);
			if (houseType != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(houseType.getHouseTypeId()))
						.withObject(houseType).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return houseType;
	}

	@Override
	public HouseType createHouseType(String typeName, long userId) {
		HouseType houseType = new HouseType();

		long houseTypeId = counterService.increment(HouseType.class.getName());

		houseType.setHouseTypeId(houseTypeId);
		houseType.setTypeName(typeName);
		houseType.setCreateDate(new Date());
		houseType.setModifiedDate(new Date());
		houseType.setUserId(userId);

		houseType = houseTypeRepository.createHouseType(houseType);
		if (houseType != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(houseType.getHouseTypeId()))
					.withObject(houseType).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return houseType;
	}

	@Override
	public HouseType deleteHouseType(long houseTypeId) {
		HouseType houseType = houseTypeRepository.deleteHouseType(houseTypeId);
		if (houseType != null) {
			String documentId = elasticsearchOperations.delete(HouseType.class, String.valueOf(houseTypeId));
			log.info("documentId: " + documentId);
		}
		return houseType;
	}

	@Override
	public Iterable<HouseType> getHouseTypes(String typeName, Integer start, Integer end) {
		return houseTypeRepository.getHouseTypes(typeName, start, end);
	}

	@Override
	public void indexing() {
		List<HouseType> houseTypes = houseTypeRepository.findAll();
		for (int i = houseTypes.size() - 1; i >= 0; i--) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(houseTypes.get(i).getHouseTypeId()))
					.withObject(houseTypes.get(i)).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
	}

}
