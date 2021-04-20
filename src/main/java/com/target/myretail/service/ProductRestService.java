package com.target.myretail.service;
/**
 * @author Mourya Mandava
 */

public interface ProductRestService {
	
	/**
	 * @param id
	 * @return ProductName
	 * @throws Exception
	 */
	public String fetchProductNameFromExternalAPI(String id) throws Exception;

}
