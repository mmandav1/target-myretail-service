package com.target.myretail.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;
/**
 * MongoConfiguration provides the configuration to make a connection to a running MongoDB instance.
 * 
 * @author Mourya Mandava
 */
@Configuration
public class MongoConfiguration {

	@Bean
	public MongoClient mongo() {
		return new MongoClient("localhost", 27017);
	}
	 
	@Bean(name="mongoTemplate")
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongo(), "local");
	}

}
