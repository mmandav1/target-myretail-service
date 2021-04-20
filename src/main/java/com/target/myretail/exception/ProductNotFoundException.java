package com.target.myretail.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ProductNotFoundException wraps all checked standard Java exception and enriches them with a custom error message and details.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author Mourya Mandava
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found.")
public class ProductNotFoundException extends RuntimeException  {

	private static final long serialVersionUID = -7859862643180900974L;
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

	public ProductNotFoundException() {

	}

	/**
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ProductNotFoundException(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	/**
	 * @param message
	 */
	public ProductNotFoundException(String message) {
		this.message = message;
	}

	/**
	 * @param message
	 * @param t
	 */
	public ProductNotFoundException(final String message, final Throwable t) {
		super(message,t);
	}

}