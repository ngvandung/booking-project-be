/**
 * 
 */
package com.booking.repository;

import java.util.List;

import com.booking.model.ExtensionCategory;

/**
 * @author ddung
 *
 */
public interface ExtensionCategoryRepository {

	public List<ExtensionCategory> findAll();
}
