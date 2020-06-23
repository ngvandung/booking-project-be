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

import com.booking.constant.HomeConstant;
import com.booking.model.Home;
import com.booking.repository.HomeRepository;
import com.booking.service.CounterService;
import com.booking.service.HomeService;

/**
 * @author ddung
 *
 */
@Service
public class HomeServiceImpl implements HomeService {

	private static final Logger log = Logger.getLogger(HomeServiceImpl.class);

	@Autowired
	private HomeRepository homeRepository;
	@Resource
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CounterService counterService;

	@Override
	public Home findById(long homeId) {
		return homeRepository.findById(homeId);
	}

	@Override
	public Home updateHome(long homeId, String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String description, int isActive, long ownerHomeId, long userId) {
		Home home = homeRepository.findById(homeId);

		if (home != null) {
			home.setBathroom(bathroom);
			home.setBedroom(bedroom);
			home.setLivingroom(livingroom);
			home.setMaxGuest(maxGuest);
			home.setDescription(description);
			home.setHomeTypeId(homeTypeId);
			home.setTypeName(typeName);
			home.setStateId(stateId);
			home.setStateName(stateName);
			home.setCityId(cityId);
			home.setCityName(cityName);
			home.setDistrictId(districtId);
			home.setDistrictName(districtName);
			home.setVillageId(villageId);
			home.setVillageName(villageName);
			home.setLinkGoogleMap(linkGoogleMap);
			home.setIsActive(isActive);
			home.setName(name);
			home.setModifiedDate(new Date());
			home.setOwnerHomeId(ownerHomeId);
			home.setPrice(price);
			home.setUserId(userId);
			home.setCategoryId(categoryId);

			home = homeRepository.updateHome(home);
			if (home != null) {
				IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(home.getHomeId()))
						.withObject(home).build();
				String documentId = elasticsearchOperations.index(indexQuery);
				log.info("documentId: " + documentId);
			}
		}

		return home;
	}

	@Override
	public Home createHome(String name, long categoryId, long homeTypeId, String typeName, long stateId,
			String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
			String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
			int maxGuest, String description, long ownerHomeId, long userId) {
		Home home = new Home();
		long homeId = counterService.increment(Home.class.getName());

		home.setHomeId(homeId);
		home.setBathroom(bathroom);
		home.setBedroom(bedroom);
		home.setLivingroom(livingroom);
		home.setMaxGuest(maxGuest);
		home.setDescription(description);
		home.setHomeTypeId(homeTypeId);
		home.setTypeName(typeName);
		home.setIsActive(HomeConstant.PENDING); // If is new home, it will waiting for accept
		home.setStateId(stateId);
		home.setStateName(stateName);
		home.setCityId(cityId);
		home.setCityName(cityName);
		home.setDistrictId(districtId);
		home.setDistrictName(districtName);
		home.setVillageId(villageId);
		home.setVillageName(villageName);
		home.setLinkGoogleMap(linkGoogleMap);
		home.setName(name);
		home.setModifiedDate(new Date());
		home.setCreateDate(new Date());
		home.setOwnerHomeId(ownerHomeId);
		home.setPrice(price);
		home.setUserId(userId);
		home.setCategoryId(categoryId);

		home = homeRepository.createHome(home);
		if (home != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(home.getHomeId())).withObject(home)
					.build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}

		return home;
	}

	@Override
	public Home deleteHome(long homeId) {
		Home home = homeRepository.deleteHome(homeId);
		if (home != null) {
			String documentId = elasticsearchOperations.delete(Home.class, String.valueOf(homeId));
			log.info("documentId: " + documentId);
		}
		return home;
	}

	@Override
	public Home updateHome(Home home) {
		home = homeRepository.updateHome(home);
		if (home != null) {
			IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(home.getHomeId())).withObject(home)
					.build();
			String documentId = elasticsearchOperations.index(indexQuery);
			log.info("documentId: " + documentId);
		}
		return home;
	}

}
