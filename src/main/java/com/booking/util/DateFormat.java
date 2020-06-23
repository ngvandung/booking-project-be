/**
 * 
 */
package com.booking.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ddung
 *
 */
public class DateFormat {

	private static final SimpleDateFormat yyyyMMdd_1 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat ddMMyyyy_1 = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat ddMMyyyy_2 = new SimpleDateFormat("dd/MM/yyy");
	private static final SimpleDateFormat ddMMyyyy_HHmmss = new SimpleDateFormat("dd/MM/yyy HH:mm:ss");

	public static Date formatDate(String strDate) throws ParseException {
		try {
			return yyyyMMdd_1.parse(strDate);
		} catch (Exception e_1) {
			try {
				return ddMMyyyy_1.parse(strDate);
			} catch (Exception e_2) {
				return ddMMyyyy_2.parse(strDate);
			}
		}
	}
	
	public static String formatDateToString_yyyyMMdd(Date date) {
		return yyyyMMdd_1.format(date);
	}
	
	public static String formatDateToString_ddMMyyyy_HHmmss(Date date) {
		return ddMMyyyy_HHmmss.format(date);
	}
}
