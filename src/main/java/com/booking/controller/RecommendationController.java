/**
 *
 */
package com.booking.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.booking.business.util.HouseBusinessFactoryUtil;
import com.booking.constant.SystemConstant;
import com.booking.model.House;
import com.booking.recommendation.K_Clusterer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class RecommendationController {

    @RequestMapping(value = "/recommendation/house/{houseId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<House> getLabelsByHouse(HttpServletRequest request, HttpSession session,
                                 @PathVariable("houseId") Long houseId) throws IOException {

        List<House> houses = HouseBusinessFactoryUtil.recommenderByKMean(houseId);

        return houses;
    }

    @RequestMapping(value = "/recommendation/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<House> getLabelsByUser(HttpServletRequest request, HttpSession session,
                                 @PathVariable("userId") Long userId) throws IOException {

        List<House> houses = HouseBusinessFactoryUtil.recommenderByCollaborativeFiltering(userId);

        return houses;
    }
}
