/**
 * 
 */
package com.booking.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.constant.SystemConstant;
import com.booking.security.PermissionCheckerFactoryUtil;
import com.booking.util.BeanUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ESController {

	private static final String ELASTIC_SERVER_API_URL = "http://localhost:9200";
	private static final Logger log = Logger.getLogger(ESController.class);

	@RequestMapping(value = "/_search", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject findAll(HttpServletRequest request, HttpSession session) throws ParseException, IOException {
		JSONParser parser = new JSONParser();
		JSONObject result = new JSONObject();

		String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		JSONObject jsonBody = (JSONObject) parser.parse(body);
		String indice = (String) jsonBody.get("indice");
		if (Arrays.stream(SystemConstant.PRIVATES).parallel().anyMatch(indice::contains)) {
			UserContext userContext = BeanUtil.getBean(UserContext.class);
			userContext = (UserContext) session.getAttribute("userContext");
			PermissionCheckerFactoryUtil.checkAdministrator(userContext);
		}
		jsonBody.remove("indice");

		try {
			URL url = new URL(ELASTIC_SERVER_API_URL + "/_search");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept", "Content-Type: application/json");
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
			osw.write(jsonBody.toJSONString());
			osw.flush();
			osw.close();

			BufferedReader br = new BufferedReader(
					new InputStreamReader((conn.getInputStream()), StandardCharsets.UTF_8));

			String output;

			StringBuilder sb = new StringBuilder();

			while ((output = br.readLine()) != null) {
				sb.append(output);
			}

			result = (JSONObject) parser.parse(sb.toString());
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}
}
