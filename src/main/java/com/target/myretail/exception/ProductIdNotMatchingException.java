package com.target.myretail.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ProductIdNotMatchingException wraps all checked standard Java exception and enriches them with a custom error message and details.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author Mourya Mandava
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product id in the request body doesn't match with id in path.")
public class ProductIdNotMatchingException extends RuntimeException  {

	private static final long serialVersionUID = 2271808859948729058L;
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

	public ProductIdNotMatchingException() {

	}

	/**
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ProductIdNotMatchingException(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	/**
	 * @param message
	 */
	public ProductIdNotMatchingException(String message) {
		this.message = message;
	}

	/**
	 * @param message
	 * @param t
	 */
	public ProductIdNotMatchingException(final String message, final Throwable t) {
		super(message,t);
	}

}