/**
 * 
 */
package com.booking.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.model.ExtensionCategoryDetail;
import com.booking.service.ExtensionCategoryDetailService;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ExtensionCategoryDetailController {
	@Autowired
	private ExtensionCategoryDetailService extensionCategoryDetailService;

	@RequestMapping(value = "/extensioncategorydetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ExtensionCategoryDetail> getCityCategoryDetails(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "extensionCategoryId", required = false) Long extensionCategoryId,
			@RequestParam(name = "extensionCategoryDetailIds", required = false) String extensionCategoryDetailIds) {

		return extensionCategoryDetailService.findExtensionCategoryDetails(extensionCategoryId,
				extensionCategoryDetailIds, 1L);

	}
}
