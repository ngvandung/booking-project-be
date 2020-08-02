/**
 * 
 */
package com.booking.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.constant.HouseConstant;
import com.booking.model.House;
import com.booking.service.HouseService;

/**
 * @author ddung
 *
 */
public class QueryUtil {

	private final static String HOUSE = House.class.getName();
	@Autowired
	private HouseService houseService;

	public Map<String, Object> getByClassPK_ClassName(long classPK, String className) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (className.equals(HOUSE)) {
			House house = houseService.findById(classPK);
			if (house.getIsActive() == HouseConstant.ACTIVE) {
				result.put("classPK", house.getHouseId());
				result.put("price", house.getPrice());
				result.put("className", House.class.getName());
				result.put("ownerId", house.getOwnerHouseId());
			}
		}
		return result;
	}
}
