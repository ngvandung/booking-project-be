/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.ExtensionCategoryDetail;

/**
 * @author ddung
 *
 */
public interface ExtensionCategoryDetailRepository {

	public List<ExtensionCategoryDetail> findExtensionCategoryDetails(Long extensionCategoryId,
			String extensionCategoryDetailIds, Long isActive);
}
