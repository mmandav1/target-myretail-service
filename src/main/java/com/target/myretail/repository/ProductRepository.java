package com.target.myretail.repository;

import com.target.myretail.domain.Product;

public interface ProductRepository {
	/**
	 * @param id
	 * @return Product
	 */
	public Product getProductById(String id);
	
	/**
	 * @param id
	 * @param price
	 * @param currencyCode
	 * @return Product
	 */
	public Product updatePriceById(String id, String price, String currencyCode);
	
}
