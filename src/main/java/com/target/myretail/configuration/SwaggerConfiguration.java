package com.target.myretail.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration provides the configuration for Product API
 * documentation.
 * 
 * @author Mourya Mandava
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo("My Retail Service implementation using REST,MONGO and Spring Boot",
				"App to demonstrate retail services using Spring Boot", "1.0.0", "Terms of Service",
				new Contact("Mourya Mandava", "target.com", "mouryamandava2@gmail.com"), "", "",
				Collections.emptyList());
	}

}
