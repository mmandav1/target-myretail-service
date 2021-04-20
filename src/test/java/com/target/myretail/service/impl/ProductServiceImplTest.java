package com.target.myretail.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.target.myretail.domain.Price;
import com.target.myretail.domain.Product;
import com.target.myretail.exception.APIException;
import com.target.myretail.exception.ProductPriceEmptyException;
import com.target.myretail.repository.ProductRepository;
import com.target.myretail.service.ProductRestService;

import junit.framework.Assert;
/** 
 * @author Mourya Mandava
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRestService productRestService;
	
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	@Test
	public void testWhenFetchProductDetailsReturnsCorrectName() throws Exception {
		
		Product mockProduct = new Product();
		mockProduct.setId("1234");
		Price price = new Price();
		price.setCurrency_code("USD");
		price.setValue("13.49");
		mockProduct.setCurrent_price(price);
		Mockito.when(productRepository.getProductById(mockProduct.getId())).thenReturn(mockProduct);
		Mockito.when(productRestService.fetchProductNameFromExternalAPI(mockProduct.getId())).thenReturn("The Big Lebowski (Blu-ray)");
		Product product = productServiceImpl.fetchProductDetails(mockProduct.getId());
		assertEquals(product.getName(),"The Big Lebowski (Blu-ray)");
	}
	
	@Test
	public void testWhenFetchProductDetailsReturnsCorrectNameNull() throws Exception {
		
		Product mockProduct = new Product();
		mockProduct.setId("1234");
		Price price = new Price();
		price.setCurrency_code("USD");
		price.setValue("13.49");
		mockProduct.setCurrent_price(price);
		Mockito.when(productRepository.getProductById(mockProduct.getId())).thenReturn(null);
		Product product = productServiceImpl.fetchProductDetails(mockProduct.getId());
		assertNull(product);
	}
	
	@Test(expected = APIException.class)
	public void testWhenFetchProductDetailsReturnsAPIException() throws Exception {
		
		Product mockProduct = new Product();
		mockProduct.setId("1234");
		Price price = new Price();
		price.setCurrency_code("USD");
		price.setValue("13.49");
		mockProduct.setCurrent_price(price);
		Mockito.when(productRepository.getProductById(mockProduct.getId())).thenReturn(mockProduct);
		Mockito.when(productRestService.fetchProductNameFromExternalAPI(mockProduct.getId())).thenThrow(APIException.class);
		Product product = productServiceImpl.fetchProductDetails(mockProduct.getId());
	}
	
	
	@Test
	public void testWhenFetchProductDetailsExternalAPIReturnsNoTitle() throws Exception {
		Product mockProduct = new Product();
		mockProduct.setId("1234");
		Price price = new Price();
		price.setCurrency_code("USD");
		price.setValue("13.49");
		mockProduct.setCurrent_price(price);
		Mockito.when(productRepository.getProductById(mockProduct.getId())).thenReturn(mockProduct);
		Mockito.when(productRestService.fetchProductNameFromExternalAPI(mockProduct.getId())).thenReturn(null);
		Product product = productServiceImpl.fetchProductDetails(mockProduct.getId());
		assertNull(product.getName());
	}
	
	@Test(expected = ProductPriceEmptyException.class)
	public void testWhenUpdateProductDetailsReturnsProductPriceEmptyException() throws Exception {
		
		Product mockProduct = new Product();
		mockProduct.setId("1234");
		Price price = new Price();
		price.setCurrency_code("USD");
		price.setValue("");
		mockProduct.setCurrent_price(price);
		Mockito.when(productRepository.updatePriceById(mockProduct.getId(),mockProduct.getCurrent_price().getValue(),mockProduct.getCurrent_price().getCurrency_code())).thenThrow(ProductPriceEmptyException.class);
		Product product = productServiceImpl.updateProduct(mockProduct.getId(),mockProduct);
	}
}
