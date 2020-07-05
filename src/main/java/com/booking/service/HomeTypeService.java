/**
 * 
 */
package com.booking.service;

import com.booking.model.HomeType;

/**
 * @author ddung
 *
 */
public interface HomeTypeService {
	
	public Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end);
	
	public HomeType findById(long homeTypeId);

	public HomeType updateHomeType(long homeTypeId, String typeName, long userId);

	public HomeType createHomeType(String typeName, long userId);

	public HomeType deleteHomeType(long homeTypeId);
	
	public void indexing();
}
