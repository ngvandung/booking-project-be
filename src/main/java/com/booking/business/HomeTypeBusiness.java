/**
 * 
 */
package com.booking.business;

import com.booking.model.HomeType;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public interface HomeTypeBusiness {
	public Iterable<HomeType> getHomeTypes(String typeName, Integer start, Integer end);

	public HomeType updateHomeType(long homeTypeId, String typeName, UserContext userContext);

	public HomeType createHomeType(String typeName, UserContext userContext);

	public HomeType deleteHomeType(long homeTypeId, UserContext userContext);

	public HomeType findById(long homeTypeId);
	
	public void indexing(UserContext userContext);
}
