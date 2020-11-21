/**
 * 
 */
package com.booking.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import com.booking.constant.HouseConstant;
import com.booking.model.House;
import com.booking.repository.HouseRepository;
import com.booking.service.CounterService;
import com.booking.service.HouseService;

/**
 * @author ddung
 *
 */
@Service
public class HouseServiceImpl implements HouseService {

	private static final Logger log = Logger.getLogger(HouseServiceImpl.class);

	@Autowired
	private HouseRepository houseRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public House findById(long houseId) {
		return houseRepository.findById(houseId);
	}

	@Override
	public House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String extensionCategoryDetailIds, String description, int isActive, long ownerHouseId,
			long userId) {
		House house = houseRepository.findById(houseId);

		if (house != null) {
			house.setBathroom(bathroom);
			house.setBedroom(bedroom);
			house.setLivingroom(livingroom);
			house.setMaxGuest(maxGuest);
			house.setDescription(description);
			house.setHouseTypeId(houseTypeId);
			house.setTypeName(typeName);
			house.setStateId(stateId);
			house.setStateName(stateName);
			house.setCityId(cityId);
			house.setCityName(cityName);
			house.setDistrictId(districtId);
			house.setDistrictName(districtName);
			house.setVillageId(villageId);
			house.setVillageName(villageName);
			house.setLinkGoogleMap(linkGoogleMap);
			house.setIsActive(isActive);
			house.setName(name);
			house.setModifiedDate(new Date());
			house.setOwnerHouseId(ownerHouseId);
			house.setPrice(price);
			house.setUserId(userId);
			house.setExtensionCategoryDetailIds(extensionCategoryDetailIds);

			house = houseRepository.updateHouse(house);
			if (house != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(house.getHouseId()))
						.withObject(house).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return house;
	}

	@Override
	public House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
			long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
			String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest,
			String extensionCategoryDetailIds, String description, long ownerHouseId, long userId) {
		House house = new House();
		long houseId = counterService.increment(House.class.getName());

		house.setHouseId(houseId);
		house.setBathroom(bathroom);
		house.setBedroom(bedroom);
		house.setLivingroom(livingroom);
		house.setMaxGuest(maxGuest);
		house.setDescription(description);
		house.setHouseTypeId(houseTypeId);
		house.setTypeName(typeName);
		house.setIsActive(HouseConstant.PENDING); // If is new house, it will waiting for accept
		house.setStateId(stateId);
		house.setStateName(stateName);
		house.setCityId(cityId);
		house.setCityName(cityName);
		house.setDistrictId(districtId);
		house.setDistrictName(districtName);
		house.setVillageId(villageId);
		house.setVillageName(villageName);
		house.setLinkGoogleMap(linkGoogleMap);
		house.setName(name);
		house.setModifiedDate(new Date());
		house.setCreateDate(new Date());
		house.setOwnerHouseId(ownerHouseId);
		house.setPrice(price);
		house.setUserId(userId);
		house.setExtensionCategoryDetailIds(extensionCategoryDetailIds);

		house = houseRepository.createHouse(house);
		if (house != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(house.getHouseId())).withObject(house)
					.build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return house;
	}

	@Override
	public House deleteHouse(long houseId) {
		House house = houseRepository.deleteHouse(houseId);
		if (house != null) {
			String documentId = elasticsearchOperations.delete(House.class, String.valueOf(houseId));
			log.info("documentId: " + documentId);
		}
		return house;
	}

	@Override
	public House updateHouse(House house) {
		house = houseRepository.updateHouse(house);
		if (house != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(house.getHouseId())).withObject(house)
					.build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return house;
	}

	@Override
	public void indexing() {
		List<House> houses = houseRepository.findAll();
		for (int i = houses.size() - 1; i >= 0; i--) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(houses.get(i).getHouseId()))
					.withObject(houses.get(i)).build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
	}

	@Override
	public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag) {
		return houseRepository.findMyHouses(ownerHouseId, flag);
	}

}
