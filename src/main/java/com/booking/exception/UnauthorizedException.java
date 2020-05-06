/**
 * 
 */
package com.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author ddung
 *
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="unauthorized")
public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}

