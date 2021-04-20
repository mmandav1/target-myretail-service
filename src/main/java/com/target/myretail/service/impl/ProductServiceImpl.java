package com.target.myretail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.target.myretail.domain.Product;
import com.target.myretail.exception.ProductPriceEmptyException;
import com.target.myretail.repository.ProductRepository;
import com.target.myretail.service.ProductRestService;
import com.target.myretail.service.ProductService;
/**
 * ProductServiceImpl is a service 
 * @author Mourya Mandava
 */
@Service
public class ProductServiceImpl implements ProductService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductRestService productRestClient;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Product fetchProductDetails(String id) throws Exception {

		// fetching the product from application database
		Product product = getProductWithPrice(id);
		if (product != null) {
			// fetching the title from external API
			String title = fetchProductName(id);
			product.setName(title);
		}
		return product;
	}

	
	@Override
	public Product updateProduct(String id, Product product) {

		if (product.getCurrent_price() != null && product.getCurrent_price().getValue() != null) {
			Product updatedProduct = updatePrice(id, product.getCurrent_price().getValue(),
					product.getCurrent_price().getCurrency_code());
			return updatedProduct;
		}
		logger.info("Product price is empty or null");
		throw new ProductPriceEmptyException("Product price shouldn't be empty. Please specify the product price.");
	}

	/**
	 * @param id
	 * @return String
	 * @throws Exception
	 */
	private String fetchProductName(String id) throws Exception {
		return productRestClient.fetchProductNameFromExternalAPI(id);
	}

	/**
	 * @param id
	 * @return Product
	 */
	private Product getProductWithPrice(String id) {
		return productRepository.getProductById(id);
	}

	/**
	 * @param id
	 * @param price
	 * @param currencyCode
	 * @return ProductPrice
	 */
	private Product updatePrice(String id, String price, String currencyCode) {
		return productRepository.updatePriceById(id, price, currencyCode);
	}

}
