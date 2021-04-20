package com.target.myretail.repository.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.target.myretail.domain.Product;
import com.target.myretail.repository.ProductRepository;

/**
 * ProductRepositoryImpl provides the functionality for fetching and updating the product details using id. 
 * 
 * @author Mourya Mandava
 */
@Repository
public class ProductRepositoryImpl implements ProductRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate mongoTemplate;

	@Value("${product.collection.name}")
	private String collectionName;

	
	/**
	 * This fetches the product details from the data store using id.
	 */
	@Override
	public Product getProductById(String id) {
		Product product = null;
		try{
			Criteria criteria = new Criteria();
			criteria = where("id").is(id);
			Query query = new Query(criteria);
			product = mongoTemplate.findOne(query, Product.class, collectionName);
		}catch(Exception e){
			logger.error("Unable to fetch data from Mongo database.", e.getMessage());
			throw e;
		}
		return product;
	}
	
	
	/**
	 * This updates the product details in data store based on id.
	 */
	@Override
	public Product updatePriceById(String id, String price, String currencyCode) {
		Product updatedProduct = null;
		try{
			Criteria criteria = where("id").is(id);
			Query query = new Query(criteria);
			Update update = new Update();
			update.set("price.value", price);
			if (currencyCode != null && !currencyCode.isEmpty()) {
				update.set("price.currency_code", currencyCode);
			}
			logger.info("Updating the price for given product id = "+id);
			updatedProduct = mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true),
					Product.class, collectionName);
		}catch(Exception e){
			logger.error("Unable to update product price in mongodb.", e.getMessage());
			throw e;
		}
		return updatedProduct;
	}

}
