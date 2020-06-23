/**
 * 
 */
package com.booking.business;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ddung
 *
 */
public interface VnPayPaymentBusiness {
	public Map<String, Object> payment(HttpServletRequest request, HttpServletResponse response, String vnpOrderInfo,
			String orderType, String bankCode, String language, String vnp_TmnCode, String vnp_hashSecret,
			double totalAmount, long bookingId) throws UnsupportedEncodingException, IOException;

	public Map<String, Object> confirm(HttpServletRequest request, long bookingId) throws UnsupportedEncodingException;
}
