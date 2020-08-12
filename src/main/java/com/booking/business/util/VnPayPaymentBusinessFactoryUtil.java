/**
 * 
 */
package com.booking.business.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.booking.business.VnPayPaymentBusiness;
import com.booking.util.BeanUtil;

/**
 * @author ddung
 *
 */
public class VnPayPaymentBusinessFactoryUtil {
	// Design pattern - Singleton
	private static VnPayPaymentBusiness _vnPayPaymentBusiness;

	public static VnPayPaymentBusiness getVnPayPaymentBusiness() {

		if (_vnPayPaymentBusiness == null) {
			_vnPayPaymentBusiness = BeanUtil.getBean(VnPayPaymentBusiness.class);
		}
		return _vnPayPaymentBusiness;
	}
	// ============================

	public static Map<String, Object> payment(HttpServletRequest request, HttpServletResponse response,
			String vnpOrderInfo, String orderType, String bankCode, String language, String vnp_TmnCode,
			String vnp_hashSecret, double totalAmount, long bookingId)
			throws UnsupportedEncodingException, IOException {
		return getVnPayPaymentBusiness().payment(request, response, vnpOrderInfo, orderType, bankCode, language,
				vnp_TmnCode, vnp_hashSecret, totalAmount, bookingId);
	}

	public static Map<String, Object> confirm(HttpServletRequest request, long bookingId)
			throws UnsupportedEncodingException {
		return getVnPayPaymentBusiness().confirm(request, bookingId);
	}
}
