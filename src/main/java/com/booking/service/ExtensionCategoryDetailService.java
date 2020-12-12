/**
 * 
 */
package com.booking.service;

import java.util.List;

import com.booking.model.ExtensionCategoryDetail;

/**
 * @author ddung
 *
 */
public interface ExtensionCategoryDetailService {

	public List<ExtensionCategoryDetail> findExtensionCategoryDetails(Long extensionCategoryId, String extensionCategoryDetailIds, Long isActive);
}
