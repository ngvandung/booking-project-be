/**
 *
 */
package com.booking.business.impl;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.booking.constant.HouseConstant;
import com.booking.recommendation.CollaborativeFiltering;
import com.booking.recommendation.K_Clusterer;
import com.booking.repository.HouseRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.HouseBusiness;
import com.booking.exception.ForbiddenException;
import com.booking.model.House;
import com.booking.model.User;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.service.HouseService;
import com.booking.service.UserService;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class HouseBusinessImpl implements HouseBusiness {

    private static final Logger log = Logger.getLogger(HouseBusinessImpl.class);

    @Autowired
    private HouseService houseService;
    @Autowired
    private UserService userService;
    @Autowired
    private HouseRepository houseRepository;

    @Override
    public House findById(long houseId) {
        return houseService.findById(houseId);
    }

    @Override
    public List<House> recommenderByKMean(long houseId) throws IOException {
        House house = findById(houseId);
        List<Double> listFeature = new ArrayList<>();

        listFeature.add((double) house.getStateId());
        listFeature.add((double) house.getCityId());
        listFeature.add((double) house.getDistrictId());
        listFeature.add((double) house.getVillageId());
        listFeature.add((double) house.getOwnerHouseId());
        listFeature.add((double) house.getMaxGuest());
        listFeature.add((double) house.getHouseTypeId());
        listFeature.add((double) house.getBathroom());
        listFeature.add((double) house.getBedroom());
        listFeature.add((double) house.getLivingroom());
        listFeature.add(house.getPrice());
        String extensionCategoryDetailIds = house.getExtensionCategoryDetailIds();

        String[] extensions = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        for (int j = 0; j < extensions.length; j++) {
            if (extensionCategoryDetailIds.contains(extensions[j])) {
                listFeature.add((double) 1);
            } else {
                listFeature.add((double) 0);
            }
        }

        double[] arrFeature = new double[listFeature.size()];
        for (int i = 0; i < arrFeature.length; i++) {
            arrFeature[i] = listFeature.get(i);
        }

        K_Clusterer kClusterer = new K_Clusterer();
        List<Double> labels = kClusterer.recommender(arrFeature);
        List<House> houses = new ArrayList<>();

        for (int i = 0; i < HouseConstant.MAX_HOUES_RECOMMENDATION; i++) {
            if (labels.get(i).longValue() != houseId) {
                House _house = houseService.findById(labels.get(i).longValue());
                houses.add(_house);
            }
        }

        log.info("===> Size: " + houses.size());
        return houses;
    }

    @Override
    public List<House> recommenderByCollaborativeFiltering(long userId) throws IOException {
        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
        double[] array = collaborativeFiltering.recommendation(userId);
        List<House> results = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= 0) {
                House house = houseService.findById(i);
                if (house != null) {
                    results.add(house);
                }
            }
        }
        return results;
    }

    @Override
    public House updateHouse(long houseId, String name, long houseTypeId, String typeName, long stateId,
                             String stateName, long cityId, String cityName, long districtId, String districtName, long villageId,
                             String villageName, String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom,
                             int maxGuest, String extensionCategoryDetailIds, String description, int isActive, long ownerHouseId,
                             UserContext userContext) {

        User user = userService.findByUserId(userContext.getUser().getUserId());
        if (user.getIsEnabled() != 1) {
            throw new ForbiddenException();
        }

        House house = houseService.findById(houseId);
        if (PermissionCheckerFactoryUtil.isOwner(userContext, house.getUserId())) {
            return houseService.updateHouse(houseId, name, houseTypeId, typeName, stateId, stateName, cityId, cityName,
                    districtId, districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom,
                    bathroom, maxGuest, extensionCategoryDetailIds, description, isActive, ownerHouseId,
                    userContext.getUser().getUserId());
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    public House createHouse(String name, long houseTypeId, String typeName, long stateId, String stateName,
                             long cityId, String cityName, long districtId, String districtName, long villageId, String villageName,
                             String linkGoogleMap, double price, int bedroom, int livingroom, int bathroom, int maxGuest,
                             String extensionCategoryDetailIds, String description, long ownerHouseId, UserContext userContext) {

        User user = userService.findByUserId(userContext.getUser().getUserId());
        if (user.getIsEnabled() != 1) {
            throw new ForbiddenException();
        }
        PermissionCheckerFactoryUtil.checkHost(userContext);

        return houseService.createHouse(name, houseTypeId, typeName, stateId, stateName, cityId, cityName, districtId,
                districtName, villageId, villageName, linkGoogleMap, price, bedroom, livingroom, bathroom, maxGuest,
                extensionCategoryDetailIds, description, ownerHouseId, userContext.getUser().getUserId());
    }

    @Override
    public House deleteHouse(long houseId, UserContext userContext) {
        User user = userService.findByUserId(userContext.getUser().getUserId());
        if (user.getIsEnabled() != 1) {
            throw new ForbiddenException();
        }
        House house = houseService.findById(houseId);
        if (PermissionCheckerFactoryUtil.isOwner(userContext, house.getUserId())) {
            return houseService.deleteHouse(houseId);
        } else {
            throw new ForbiddenException();
        }
    }

    @Override
    public House actionHouse(long houseId, int status, UserContext userContext) {
        PermissionCheckerFactoryUtil.checkManager(userContext);

        House house = findById(houseId);
        if (house != null) {
            house.setIsActive(status);
            house.setModifiedDate(new Date());

            house = houseService.updateHouse(house);
        }
        return house;
    }

    @Override
    public void indexing(UserContext userContext) {
        houseService.indexing();
    }

    @Override
    public List<Map<String, Object>> findMyHouses(Long ownerHouseId, String flag, UserContext userContext) {
        if (PermissionCheckerFactoryUtil.isOwner(userContext, ownerHouseId)) {
            return houseService.findMyHouses(ownerHouseId, flag);
        } else {
            throw new ForbiddenException();
        }
    }

}
