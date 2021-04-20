package com.target.myretail.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The APIException wraps all checked standard Java exception and enriches them with a custom error message and details.
 * You can use this code to retrieve localized error messages.
 * 
 * @author Mourya Mandava
 */

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to fetch product name from downstream API.")
public class APIException extends RuntimeException  {

	private static final long serialVersionUID = -8872144194372799506L;
	private Date timestamp;
	private String message;
	private String details;

	/**
	 * @return
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	public APIException() {

	}

	/**
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public APIException(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	
	/**
	 * @param message
	 */
	public APIException(String message) {
		this.message = message;
	}

	/**
	 * @param message
	 * @param t
	 */
	public APIException(final String message, final Throwable t) {
		super(message,t);
	}

}