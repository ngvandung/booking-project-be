/**
 * 
 */
package com.booking.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.booking.model.Home;
import com.booking.service.HomeService;

/**
 * @author ddung
 *
 */
public class QueryUtil {

	private final static String HOME = Home.class.getName();
	@Autowired
	private HomeService homeService;

	public Map<String, Object> getByClassPK_ClassName(long classPK, String className) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (className.equals(HOME)) {
			Home home = homeService.findById(classPK);
			result.put("classPK", home.getHomeId());
			result.put("price", home.getPrice());
		}
		return result;
	}
}
