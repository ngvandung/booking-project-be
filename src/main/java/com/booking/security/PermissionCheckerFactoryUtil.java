/**
 * 
 */
package com.booking.security;

//import org.apache.log4j.Logger;

import com.booking.exception.ForbiddenException;
import com.booking.exception.UnauthorizedException;
import com.booking.util.AuthServiceUtil;
import com.booking.util.UserContext;

/**
 * @author ddung
 *
 */
public class PermissionCheckerFactoryUtil {
	// private static final Logger log =
	// Logger.getLogger(PermissionCheckerFactoryUtil.class);

	public static void checkManagerOrAdministrator(UserContext userContext) {
		if (userContext == null) {
			throw new UnauthorizedException();
		}

		if (!userContext.isSignin()) {
			throw new UnauthorizedException();
		}

		String permission = userContext.getPermission();
		if (permission.equals(AuthServiceUtil.DefaultRole.ADMIN.toString())
				|| permission.equals(AuthServiceUtil.DefaultRole.MANAGER.toString())) {
			return;
		} else {
			throw new ForbiddenException();
		}
	}

	public static void checkAdministrator(UserContext userContext) {
		if (userContext == null) {
			throw new UnauthorizedException();
		}

		if (!userContext.isSignin()) {
			throw new UnauthorizedException();
		}

		String permission = userContext.getPermission();
		if (!permission.equals(AuthServiceUtil.DefaultRole.ADMIN.toString())) {
			throw new ForbiddenException();
		}
	}
	
	public static void checkHost(UserContext userContext) {
		if (userContext == null) {
			throw new UnauthorizedException();
		}

		if (!userContext.isSignin()) {
			throw new UnauthorizedException();
		}

		String permission = userContext.getPermission();
		if (!permission.equals(AuthServiceUtil.DefaultRole.HOST.toString())) {
			throw new ForbiddenException();
		}
	}

	public static void checkManager(UserContext userContext) {
		if (userContext == null) {
			throw new UnauthorizedException();
		}

		if (!userContext.isSignin()) {
			throw new UnauthorizedException();
		}

		String permission = userContext.getPermission();
		if (!permission.equals(AuthServiceUtil.DefaultRole.MANAGER.toString())) {
			throw new ForbiddenException();
		}
	}

	public static void checkAuthentication(UserContext userContext) {
		if (userContext == null) {
			throw new UnauthorizedException();
		}

		if (!userContext.isSignin()) {
			throw new UnauthorizedException();
		}
	}

	public static boolean isOwner(UserContext userContext, long resourceUserId) {

		checkAuthentication(userContext);

		String permission = userContext.getPermission();
		long curUserId = userContext.getUser().getUserId();

		if (permission.equals(AuthServiceUtil.DefaultRole.ADMIN.toString()) || resourceUserId == curUserId) {
			return true;
		}

		return false;
	}
	
	

}
