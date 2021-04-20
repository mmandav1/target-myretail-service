package com.target.myretail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/** 
 * @author Mourya Mandava
 */
@SpringBootApplication
@ComponentScan("com.target")
public class TargetMyretailServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TargetMyretailServiceApplication.class, args);
	}
	
	@Bean(name = "restTemplate")
	public RestTemplate getRestTemplate(){
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(30 * 8000); 
        httpRequestFactory.setConnectTimeout(30 * 8000);
        httpRequestFactory.setReadTimeout(30 * 8000);
		return new RestTemplate(httpRequestFactory);
	}

}
