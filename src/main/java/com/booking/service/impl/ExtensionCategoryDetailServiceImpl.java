/**
 * 
 */
package com.booking.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.model.ExtensionCategoryDetail;
import com.booking.repository.ExtensionCategoryDetailRepository;
import com.booking.service.ExtensionCategoryDetailService;

/**
 * @author ddung
 *
 */
@Service
public class ExtensionCategoryDetailServiceImpl implements ExtensionCategoryDetailService {

	@Autowired
	private ExtensionCategoryDetailRepository extensionCategoryDetailRepository;

	@Override
	public List<ExtensionCategoryDetail> findExtensionCategoryDetails(Long extensionCategoryId,
			String extensionCategoryDetailIds, Long isActive) {
		return extensionCategoryDetailRepository.findExtensionCategoryDetails(extensionCategoryId,
				extensionCategoryDetailIds, isActive);
	}

}
