/**
 * 
 */
package com.booking.util;

import java.util.Date;

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
}
