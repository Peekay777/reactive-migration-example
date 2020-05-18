package com.koutsios.reactivemigrationexample.controller;

import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aBanana;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aProductDto;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.anApple;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Flux.fromIterable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.exception.ProductNotFoundException;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = ProductController.class)
public class ProductControllerTest {

  @Autowired
  private WebTestClient webTestClient;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductService productService;

  @Test
  void getAllProductsTest() throws Exception {
    when(productService.getAllProducts()).thenReturn(Flux.fromIterable(Arrays.asList(anApple(), aBanana())));

    webTestClient.get()
        .uri("/products/")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(new ParameterizedTypeReference<Product>() {})
        .hasSize(2);

    verify(productService).getAllProducts();
  }

  @Test
  void getProductTestGivenValidProductId() throws Exception {
    when(productService.getProduct(anyString())).thenReturn(Mono.just(anApple()));

    webTestClient.get()
        .uri("/products/000001")
        .exchange()
        .expectStatus().isOk();

    verify(productService).getProduct(anyString());
  }

  @Test
  void getProductTestGivenInvalidProductId() throws Exception {
    String productId = "000001";
    when(productService.getProduct(anyString())).thenThrow(new ProductNotFoundException(productId));

    String actualResponseBody = webTestClient.get()
        .uri("/products/{id}", productId)
        .exchange()
        .expectStatus().isNotFound()
        .returnResult(String.class)
        .getResponseBody()
        .blockLast();

    assertEquals("Could not find product 000001", actualResponseBody);
    verify(productService).getProduct(anyString());
  }

  @Test
  void createProductTest() throws Exception {
    ProductDto newProduct = aProductDto();
    when(productService.createProduct(any(ProductDto.class))).thenReturn(Mono.just(anApple()));

    webTestClient.post()
        .uri("/products")
        .contentType(APPLICATION_JSON)
        .bodyValue(newProduct)
        .exchange()
        .expectStatus().isOk();

    verify(productService).createProduct(any(ProductDto.class));
  }

  @Test
  void updateProductTestGivenValidProductId() throws Exception {
    String productId = "000001";
    ProductDto amendProduct = aProductDto();
    when(productService.updateProduct(anyString(), any(ProductDto.class))).thenReturn(Mono.just(anApple()));

    webTestClient.put()
        .uri("/products/{id}", productId)
        .contentType(APPLICATION_JSON)
        .bodyValue(amendProduct)
        .exchange()
        .expectStatus().isOk();

    verify(productService).updateProduct(anyString(), any(ProductDto.class));
  }

  @Test
  void updateProductTestGivenInvalidProductId() throws Exception {
    String productId = "000001";
    ProductDto amendProduct = aProductDto();
    when(productService.updateProduct(anyString(), any(ProductDto.class))).thenThrow(new ProductNotFoundException(productId));

    String actualResponseBody = webTestClient.put()
        .uri("/products/{id}", productId)
        .contentType(APPLICATION_JSON)
        .bodyValue(amendProduct)
        .exchange()
        .expectStatus().isNotFound()
        .returnResult(String.class)
        .getResponseBody()
        .blockLast();

    assertEquals("Could not find product 000001", actualResponseBody);
    verify(productService).updateProduct(anyString(), any(ProductDto.class));
  }

  @Test
  void deleteProductTestGivenValidProductId() throws Exception {
    String productId = "000001";

    webTestClient.delete()
        .uri("/products/{id}", productId)
        .exchange()
        .expectStatus().isOk();

    verify(productService).deleteProduct(anyString());
  }

}
