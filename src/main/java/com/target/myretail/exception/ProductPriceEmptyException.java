package com.target.myretail.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ProductPriceEmptyException wraps all checked standard Java exception and enriches them with a custom error message and details.
 * You can use this code to retrieve localized error messages and to link to our online documentation.
 * 
 * @author Mourya Mandava
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Product price is empty in the payload.")
public class ProductPriceEmptyException extends RuntimeException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3386333153104789842L;
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

	public ProductPriceEmptyException() {

	}

	/**
	 * @param timestamp
	 * @param message
	 * @param details
	 */
	public ProductPriceEmptyException(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	/**
	 * @param message
	 */
	public ProductPriceEmptyException(String message) {
		this.message = message;
	}

	/**
	 * @param message
	 * @param t
	 */
	public ProductPriceEmptyException(final String message, final Throwable t) {
		super(message,t);
	}

}