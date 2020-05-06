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
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Not Acceptable")
public class NotAcceptableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
