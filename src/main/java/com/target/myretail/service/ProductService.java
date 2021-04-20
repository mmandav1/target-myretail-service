package com.target.myretail.service;

import com.target.myretail.domain.Product;
/**
 * @author Mourya Mandava
 */
public interface ProductService {

	/**
	 * @param id
	 * @return Product
	 * @throws Exception
	 */
	public Product fetchProductDetails(String id) throws Exception;
	
	/**
	 * @param id
	 * @param Product
	 * @return Product
	 */
	public Product updateProduct(String id, Product product);

}
