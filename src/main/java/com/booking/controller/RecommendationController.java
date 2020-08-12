///**
// * 
// */
//package com.booking.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.http.MediaType;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.booking.model.Location;
//import com.booking.recommendation.TravelRecommendation;
//
///**
// * @author ddung
// *
// */
//@RestController
//@RequestMapping("/api/v1")
//public class RecommendationController {
//
//	@RequestMapping(value = "/recommendation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public List<Location> getHouseTypes(HttpServletRequest request, HttpSession session,
//			@RequestParam(name = "country") String country, @RequestParam(name = "state") String state,
//			@RequestParam(name = "type") String type, @RequestParam(name = "userId") Integer userId) {
//
//		List<Location> locations = TravelRecommendation.getInstance().getFilteredRecommendation(country, state, type,
//				userId);
//
//		return locations;
//	}
//}
