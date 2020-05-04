package com.koutsios.reactivemigrationexample.controller;

import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aBanana;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aProductDto;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.anApple;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.exception.ProductNotFoundException;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  @Test
  void getAllProductsTest() throws Exception {
    when(productService.getAllProducts()).thenReturn(Arrays.asList(anApple(), aBanana()));

    mockMvc.perform(get("/products/").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));

    verify(productService).getAllProducts();
  }

  @Test
  void getProductTestGivenValidProductId() throws Exception {
    when(productService.getProduct(anyString())).thenReturn(anApple());

    mockMvc.perform(get("/products/000001").contentType(APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(productService).getProduct(anyString());
  }

  @Test
  void getProductTestGivenInvalidProductId() throws Exception {
    String productId = "000001";
    when(productService.getProduct(anyString())).thenThrow(new ProductNotFoundException(productId));

    MvcResult response = mockMvc.perform(get("/products/{id}", productId).contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
    String actualResponseBody = response.getResponse().getContentAsString();

    assertEquals("Could not find product 000001", actualResponseBody);
    verify(productService).getProduct(anyString());
  }

  @Test
  void createProductTest() throws Exception {
    ProductDto newProduct = aProductDto();
    when(productService.createProduct(any(ProductDto.class))).thenReturn(anApple());

    mockMvc.perform(post("/products")
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newProduct)))
        .andExpect(status().isOk());

    verify(productService).createProduct(any(ProductDto.class));
  }

  @Test
  void updateProductTestGivenValidProductId() throws Exception {
    String productId = "000001";
    ProductDto newProduct = aProductDto();
    when(productService.updateProduct(anyString(), any(ProductDto.class))).thenReturn(anApple());

    mockMvc.perform(put("/products/{id}", productId)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newProduct)))
        .andExpect(status().isOk());

    verify(productService).updateProduct(anyString(), any(ProductDto.class));
  }

  @Test
  void updateProductTestGivenInvalidProductId() throws Exception {
    String productId = "000001";
    ProductDto newProduct = aProductDto();
    when(productService.updateProduct(anyString(), any(ProductDto.class))).thenThrow(new ProductNotFoundException(productId));

    MvcResult response = mockMvc.perform(put("/products/{id}", productId)
        .contentType(APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newProduct)))
        .andExpect(status().isNotFound())
        .andReturn();
    String actualResponseBody = response.getResponse().getContentAsString();

    assertEquals("Could not find product 000001", actualResponseBody);
    verify(productService).updateProduct(anyString(), any(ProductDto.class));
  }

  @Test
  void deleteProductTestGivenValidProductId() throws Exception {
    String productId = "000001";
    mockMvc.perform(delete("/products/{id}", productId)
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(productService).deleteProduct(anyString());
  }

  @Test
  void deleteProductTestGivenInvalidProductId() throws Exception {
    String productId = "000001";
    doThrow(new ProductNotFoundException(productId)).when(productService).deleteProduct(anyString());

    MvcResult response = mockMvc.perform(delete("/products/{id}", productId)
        .contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andReturn();
    String actualResponseBody = response.getResponse().getContentAsString();

    assertEquals("Could not find product 000001", actualResponseBody);
    verify(productService).deleteProduct(anyString());
  }

}
