/**
 * 
 */
package com.booking.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ddung
 *
 */
public class RentUtil {

	public static double calculateRentTime(Date from, Date to) {
		try {
			long diff = to.getTime() - from.getTime();

			double diffHours = Double.valueOf(diff / Double.valueOf(60 * 60 * 1000));
			double diffDays = Double.valueOf(diffHours / 24);

			return diffDays;
		} catch (Exception e) {
			return 0;
		}
	}

	public static List<Map<String, Object>> convertObjectToMap(List<Object[]> obj) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		obj.parallelStream().forEach(el -> {
			Map<String, Object> map = RentUtil.mergeObject(el[0], el[1]);
			result.add(map);
		});

		return result;
	}

	public static Map<String, Object> mergeObject(Object obj1, Object obj2) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (obj1 != null) {
			for (Field field : obj1.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					result.put(field.getName(), field.get(obj1));
				} catch (Exception e) {
				}
			}
		}

		if (obj2 != null) {
			for (Field field : obj2.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					result.put(field.getName(), field.get(obj2));
				} catch (Exception e) {
				}
			}
		}

		return result;
	}
}
