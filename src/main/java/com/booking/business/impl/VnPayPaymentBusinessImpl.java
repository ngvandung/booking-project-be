/**
 * 
 */
package com.booking.business.impl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.booking.business.VnPayPaymentBusiness;
import com.booking.constant.BookingConstant;
import com.booking.model.Booking;
import com.booking.model.House;
import com.booking.model.User;
import com.booking.service.BookingService;
import com.booking.service.HouseService;
import com.booking.service.MessageQueueService;
import com.booking.service.UserService;
import com.booking.util.ConfigVnPay;
import com.booking.util.ZXingHelper;

/**
 * @author ddung
 *
 */
public class VnPayPaymentBusinessImpl implements VnPayPaymentBusiness {

	private String realPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
	private String pathFolder = realPath.substring(realPath.lastIndexOf("C:"), realPath.lastIndexOf("/booking"))
			+ "/publics/booking/";

	@Autowired
	private UserService userService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private BookingService bookingService;
	@Autowired
	private MessageQueueService messageQueueService;

	@Override
	public Map<String, Object> payment(HttpServletRequest request, HttpServletResponse response, String vnpOrderInfo,
			String orderType, String bankCode, String language, String vnp_TmnCode, String vnp_hashSecret,
			double totalAmount, long bookingId) throws IOException {

		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", "2.0.0");
		vnp_Params.put("vnp_Command", "pay");
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(((int) totalAmount * 100)));
		vnp_Params.put("vnp_CurrCode", "VND");
		if (bankCode != null && bankCode.isEmpty()) {
			vnp_Params.put("vnp_BankCode", bankCode);
		}
		vnp_Params.put("vnp_TxnRef", String.valueOf(bookingId));
		vnp_Params.put("vnp_OrderInfo", vnpOrderInfo);
		vnp_Params.put("vnp_OrderType", orderType);

		if (language != null && !language.isEmpty()) {
			vnp_Params.put("vnp_Locale", language);
		} else {
			vnp_Params.put("vnp_Locale", "vn");
		}
		vnp_Params.put("vnp_ReturnUrl", ConfigVnPay.vnp_Returnurl);
		vnp_Params.put("vnp_IpAddr", ConfigVnPay.getIpAddress(request));

		Date dt = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(dt);
		String vnp_CreateDate = dateString;
		vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

		// Build data to hash and querystring
		List fieldNames = new ArrayList(vnp_Params.keySet());
		Collections.sort(fieldNames);
		StringBuilder hashData = new StringBuilder();
		StringBuilder query = new StringBuilder();
		Iterator itr = fieldNames.iterator();
		while (itr.hasNext()) {
			String fieldName = (String) itr.next();
			String fieldValue = (String) vnp_Params.get(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				// Build hash data
				hashData.append(fieldName);
				hashData.append('=');
				hashData.append(fieldValue);
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = ConfigVnPay.Sha256(vnp_hashSecret + hashData.toString());
		queryUrl += "&vnp_SecureHashType=SHA256&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = ConfigVnPay.vnp_PayUrl + "?" + queryUrl;
		result.put("code", "00");
		result.put("message", "success");
		result.put("data", paymentUrl);

		return result;
	}

	@Override
	public Map<String, Object> confirm(HttpServletRequest request, long bookingId) throws UnsupportedEncodingException {
		Map fields = new HashMap();
		for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
			String fieldName = (String) params.nextElement();
			String fieldValue = request.getParameter(fieldName);
			if ((fieldValue != null) && (fieldValue.length() > 0)) {
				fields.put(fieldName, fieldValue);
			}
		}

		if (fields.containsKey("vnp_SecureHashType")) {
			fields.remove("vnp_SecureHashType");
		}
		String vnp_SecureHash = "";
		if (fields.containsKey("vnp_SecureHash")) {
			vnp_SecureHash = (String) fields.get("vnp_SecureHash");
			fields.remove("vnp_SecureHash");
		}
		Booking booking = bookingService.findById(bookingId);
		long classPK = booking.getClassPK();
		String className = booking.getClassName();
		long userId = 0;
		if (className.equals(House.class.getName())) {
			House house = houseService.findById(classPK);
			userId = house.getUserId();
		}
		User user = userService.findByUserId(userId);
		String signValue = ConfigVnPay.hashAllFields(fields, user.getHashSecret());
		Map<String, Object> result = new HashMap<String, Object>();
		if (vnp_SecureHash.equals(signValue)) {
			if (((String) fields.get("vnp_ResponseCode")).equals("00")) {
				String pathQRCodeImg = pathFolder + File.separator + "images" + File.separator + "qrCode_"
						+ System.currentTimeMillis() + ".png";
				// Create QRCode
				try {
					StringBuilder contentQRCode = new StringBuilder();
					contentQRCode.append("Email: " + booking.getEmail() + " - Phone: " + booking.getPhone());
					if (booking.getClassName().equals(House.class.getName())) {
						contentQRCode.append(" - House: " + booking.getClassPK());
					}
					contentQRCode.append(" - Total Amount: " + booking.getTotalAmount());

					byte[] bs = ZXingHelper.getQRCodeImage(contentQRCode.toString(), 500, 500);

					FileUtils.writeByteArrayToFile(new File(pathQRCodeImg), bs);

					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(bs, false)));
					booking.setQrCode(sb.toString());
				} catch (Exception e) {
				}
				// Da thanh toan
				booking.setBookingStatus(BookingConstant.RENTING);
				booking = bookingService.updateBooking(booking);

				// Add vao queue de gui email cho nguoi thue
				House house = houseService.findById(classPK);
				JSONObject jPayload = new JSONObject();
				jPayload.put("toEmail", booking.getEmail());
				jPayload.put("houseName", house.getName());
				jPayload.put("pathQRCode", pathQRCodeImg);
				messageQueueService.createMessageQueue("", 0L, "email", jPayload.toJSONString());

				result.put("status", 200);
				result.put("message", "successfully");
			} else {
				result.put("status", 400);
				result.put("message", "Payment Failed");
			}
		} else {
			result.put("status", 400);
			result.put("message", "failed");
		}

		return result;
	}

}
