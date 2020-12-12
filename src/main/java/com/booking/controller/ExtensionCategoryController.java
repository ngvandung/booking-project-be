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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.model.ExtensionCategory;
import com.booking.service.ExtensionCategoryService;

/**
 * @author ddung
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ExtensionCategoryController {

	@Autowired
	private ExtensionCategoryService extensionCategoryService;

	@RequestMapping(value = "/extensioncategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<ExtensionCategory> getCityCategories(HttpServletRequest request, HttpSession session) {

		return extensionCategoryService.findAll();

	}
}
