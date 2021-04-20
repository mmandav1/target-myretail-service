package com.target.myretail.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.target.myretail.domain.Price;
import com.target.myretail.domain.Product;
import com.target.myretail.exception.APIException;
import com.target.myretail.exception.ProductPriceEmptyException;
import com.target.myretail.service.ProductService;

/** 
 * @author Mourya Mandava
 */
@WebMvcTest(value = ProductController.class)
@RunWith(SpringRunner.class)
public class ProductControllerTest {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	ProductService productServiceMock;
	
	/**
	 * Mockito Setup before any test run.
	 */
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * @throws Exception
	 * Positive test case
	 */
	@Test
	public void getProductDetailsTest() throws Exception{
		Product mockProduct = new Product();
		Price price = new Price();
		mockProduct.setId("1234");
		mockProduct.setName("The Big Lebowski (Blu-ray)");
		price.setCurrency_code("USD");
		price.setValue("13.49");
		mockProduct.setCurrent_price(price);
		
		Mockito.when(productServiceMock.fetchProductDetails(Mockito.anyString())).thenReturn(mockProduct);
		
		String url = "/products/1234";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE);
		
		// Actual Result
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		// Expected Result
		String expectedProductJson = "{\"id\": \"1234\",\"name\": \"The Big Lebowski (Blu-ray)\",\"current_price\": {\"value\": \"13.49\",\"currency_code\": \"USD\"}}";

		JSONAssert.assertEquals(expectedProductJson, result.getResponse().getContentAsString(), false);
	}
	
	/**
	 * @throws Exception
	 * GET ProductNotFoundException Test
	 */
	@Test
	public void getProductDetailsTest_ivalidProductId() throws Exception{
		Mockito.when(productServiceMock.fetchProductDetails(Mockito.anyString())).thenReturn(null);

		String url = "/products/1234";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals("Product not found.", result.getResponse().getErrorMessage());
	}
	
	/**
	 * @throws Exception
	 * ExternalAPIException Test
	 */
	@Test
	public void getProductDetailsTest_externalAPIException() throws Exception{
		Mockito.when(productServiceMock.fetchProductDetails(Mockito.anyString())).thenThrow(new APIException());

		String url = "/products/15117729";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals("Failed to fetch product name from downstream API.", result.getResponse().getErrorMessage());
	}
	
	/**
	 * @throws Exception
	 * InternalServerException Test
	 */
	@Test
	public void getProductDetailsTest_internalServerException() throws Exception{
		Mockito.when(productServiceMock.fetchProductDetails(Mockito.anyString())).thenThrow(new RuntimeException());

		String url = "/products/78089";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(500, result.getResponse().getStatus());
	}
	
	/**
	 * @throws Exception
	 * Positive Test
	 */
	@Test
	public void updateProductPriceTest() throws Exception {
		Product mockProduct = new Product();
		Price price = new Price();
		mockProduct.setId("1234");
		price.setCurrency_code("USD");
		price.setValue("159.99");
		mockProduct.setCurrent_price(price);
		
		Mockito.when(productServiceMock.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(mockProduct);
		
		String url = "/products/1234";
		String body = "{\"id\": \"1234\",\"current_price\": {\"value\": \"159.99\",\"currency_code\": \"USD\"}}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).content(body).contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON);
		
		// Actual Result
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		// Expected Result
		String expectedProductJson = "{\"id\": \"1234\",\"current_price\": {\"value\": \"159.99\",\"currency_code\": \"USD\"}}";

		JSONAssert.assertEquals(expectedProductJson, result.getResponse().getContentAsString(), false);
		
	}
	
	/**
	 * @throws Exception
	 * EmptyProductPriceException Test
	 */
	@Test
	public void updateProductPriceTest_emptyPrice() throws Exception {
		Mockito.when(productServiceMock.updateProduct(Mockito.anyString(), Mockito.any())).thenThrow(new ProductPriceEmptyException());
		
		String url = "/products/1234";
		String body = "{\"id\": \"1234\"}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).content(body).contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("Product price is empty in the payload.", result.getResponse().getErrorMessage());
		
	}
	
	/**
	 * @throws Exception
	 * ProductIdMisMatchException Test
	 */
	@Test
	public void updateProductPriceTest_idMismatch() throws Exception {
		
		String url = "/products/1234";
		String body = "{\"id\": \"1234456\",\"current_price\": {\"value\": \"159.99\",\"currency_code\": \"USD\"}}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).content(body).contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("Product id in the request body doesn't match with id in path.", result.getResponse().getErrorMessage());
		
	}
	
	/**
	 * @throws Exception
	 * PUT ProductNotFoundException Test
	 */
	@Test
	public void updateProductPriceTest_productNotFound() throws Exception {
		Mockito.when(productServiceMock.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(null);
		
		String url = "/products/1234456";
		String body = "{\"id\": \"1234456\",\"current_price\": {\"value\": \"159.99\",\"currency_code\": \"USD\"}}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(url).content(body).contentType(MediaType.APPLICATION_JSON)
	            .accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals("Product not found.", result.getResponse().getErrorMessage());
		
	}
}
