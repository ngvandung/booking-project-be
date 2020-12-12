/**
 * 
 */
package com.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.ExtensionCategory;
import com.booking.repository.ExtensionCategoryRepository;
import com.booking.service.ExtensionCategoryService;

/**
 * @author ddung
 *
 */
@Service
public class ExtensionCategoryServiceImpl implements ExtensionCategoryService {

	@Autowired
	private ExtensionCategoryRepository extensionCategoryRepository;

	@Override
	public List<ExtensionCategory> findAll() {
		return extensionCategoryRepository.findAll();
	}

}
