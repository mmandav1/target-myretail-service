package com.target.myretail.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.target.myretail.domain.Product;
import com.target.myretail.exception.APIException;
import com.target.myretail.exception.ProductIdNotMatchingException;
import com.target.myretail.exception.ProductNotFoundException;
import com.target.myretail.service.ProductService;

import io.swagger.annotations.ApiOperation;

/**
 * The ProductController is responsible for processing incoming REST API requests,
 * preparing a model and returning the view to be rendered as a response.
 * @author Mourya Mandava
 */
@RestController
@RequestMapping("/products")
public class ProductController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;

	/**
	 * @param productId
	 * @return Product
	 * @throws Exception 
	 * This API can be used to fetch Product by productId.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Fetch Product details for the given product id ")
	public ResponseEntity<Product> fetchProductDetails(
			@PathVariable(value = "id", required = true) String productId) throws Exception{
		logger.info("In getProductDetails method.");
		Product product;
		try {
			product = productService.fetchProductDetails(productId);
		} catch(APIException e){
			throw e;
		}catch (Exception e) {
			logger.error("Error occurred while getting product details.", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (product == null)
			throw new ProductNotFoundException("Requested product does not exists in the store.");
		return new ResponseEntity<>(product, HttpStatus.OK);

	}

	/**
	 * @param id
	 * @return productInfo
	 * This API returns productInfo as a string. This API is a 
	 * substitute for http://redsky.target.com/v3/* an external API.
	 */
	@RequestMapping(value = "/v3/pdp/tcin/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Fetch Product Name for the given product id ")
	public ResponseEntity<String> fetchProductName(@PathVariable(value = "id", required = true) String id) {
		logger.info("Inside fetchProductName method.");
		Map<String, String> namesMap = getProductNames();
		String productInfo = namesMap.get(id);
		if(productInfo != null){
			return new ResponseEntity<>(productInfo, HttpStatus.OK);
		}
		throw new ProductNotFoundException("Requested product name does not exists.");
	}

	/**
	 * @param id
	 * @param product
	 * @return Product
	 * This API updates product's price for a given productId and
	 * product object in requestBody.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Update Product details for the given product id ")
	public ResponseEntity<Product> updateProductPrice(
			@PathVariable(value = "id", required = true) String id, @RequestBody Product product) throws Exception{
		logger.info("In updateProductPrice method.");
		if (!id.equalsIgnoreCase(product.getId())) {
			throw new ProductIdNotMatchingException("Product Id is not matching with request body.");
		}
		Product newProduct;
		try {
			newProduct = productService.updateProduct(id, product);
		} catch (Exception e) {
			logger.error("Exception occurred while updating product price.", e);
			throw e;
		}
		if (newProduct == null)
			throw new ProductNotFoundException("Requested product does not exists.");
		return new ResponseEntity<>(newProduct, HttpStatus.OK);
	}
	
	private Map<String, String> getProductNames() {
		Map<String, String> namesMap = new HashMap<>();
		String productInfo1 = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"iPhone X 64GB - Space Gray\"}}}}";
		namesMap.put("13860428",productInfo1);
		String productInfo2 = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"Dell Latitude E7470 14-inch\"}}}}";
		namesMap.put("54456119",productInfo2);
		String productInfo3 = "{\"product\": {\"item\": {\"product_description\": {\"title\": \"The Big Lebowski (Blu-ray)\"}}}}";
		namesMap.put("12954218",productInfo3);
		return namesMap;
	}

}
