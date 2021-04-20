package com.target.myretail.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.target.myretail.exception.APIException;
import com.target.myretail.service.ProductRestService;
import com.target.myretail.util.MyRetailUtil;
/**
 * ProductRestClientServiceImpl is a Rest Client for making external API Call.
 * @author Mourya Mandava
 */
@Component
public class ProductRestServiceImpl implements ProductRestService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestTemplate restTemplate;

	@Value("${redsky.target.service}")
	private String baseurl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.target.myretail.service.ProductRestClientService#
	 * fetchProductNameFromExternalAPI(java.lang.String)
	 */
	@Override
	public String fetchProductNameFromExternalAPI(String id) throws Exception {
		String url = buildExtUrl(id);
		ResponseEntity<String> jsonObj = null;
		String name = null;
		try {
			logger.info("Fetching product name from external API.");
			jsonObj = restTemplate.getForEntity(url, String.class);
			if (jsonObj != null)
				name = extractName(jsonObj.getBody());
		} catch (Exception e) {
			logger.error("Unable to fetch product name from external API.", e.getMessage());
			throw new APIException(MyRetailUtil.convertToDateViaInstant(LocalDateTime.now()), "Unable to fetch from external API.", e.getMessage());
		}
		return name;
	}

	/**
	 * @param id
	 * @return external API URL
	 */
	private String buildExtUrl(String id) {
		return baseurl + "/" + id;
	}

	/**
	 * @param jsonObj
	 * @return productName
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * This method extracts product title from the productInfo json object.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String extractName(String jsonObject) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Map> objMap = mapper.readValue(jsonObject, Map.class);
		Map<String, Map> productMap = objMap.get("product");
		Map<String, Map> itemMap = productMap.get("item");
		Map<String, String> prodDescriptionMap = itemMap.get(("product_description"));
		return prodDescriptionMap.get("title");
	}
}
