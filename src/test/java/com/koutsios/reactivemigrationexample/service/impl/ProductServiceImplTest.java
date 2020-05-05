package com.koutsios.reactivemigrationexample.service.impl;

import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aBanana;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.aProductDto;
import static com.koutsios.reactivemigrationexample.fixture.ProductFixture.anApple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.exception.ProductNotFoundException;
import com.koutsios.reactivemigrationexample.repository.ProductRepository;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceImplTest {

  @Autowired
  private ProductService productService;

  @MockBean
  private ProductRepository repository;

  @Test
  void getProductTestGivenValidId() {
    String productId = "000001";
    Product expectedProduct = anApple();
    when(repository.findById(anyString())).thenReturn(Optional.of(expectedProduct));

    Product actualProduct = productService.getProduct(productId);

    assertNotNull(actualProduct);
    assertSame(expectedProduct, actualProduct);
    verify(repository).findById(anyString());
  }

  @Test()
  void getProductTestGivenInvalidId() {
    String productId = "000001";
    when(repository.findById(anyString())).thenReturn(Optional.empty());

    assertThrows(ProductNotFoundException.class, () -> productService.getProduct(productId));
    verify(repository).findById(anyString());
  }

  @Test
  void getAllProductTest() {
    List<Product> expectedProducts = Arrays.asList(anApple(), aBanana());
    when(repository.findAll()).thenReturn(expectedProducts);

    List<Product> actualProducts = productService.getAllProducts();

    assertEquals(2, actualProducts.size());
    verify(repository).findAll();
  }

  @Test
  void createProductTest() {
    ProductDto appleDto = aProductDto();
    when(repository.save(any(Product.class))).thenReturn(anApple());

    Product actualProduct = productService.createProduct(appleDto);

    assertNotNull(actualProduct.getId());
    assertEquals(appleDto.getName(), actualProduct.getName());
    assertEquals(appleDto.getDescription(), actualProduct.getDescription());
    assertEquals(appleDto.getPrice(), actualProduct.getPrice());
    verify(repository).save(any(Product.class));
  }

  @Test
  void updateProductTestGivenValidId() {
    String productId = "000001";
    Product expectedProduct = anApple();
    expectedProduct.setPrice(5.00);
    ProductDto amendProduct = ProductDto.builder()
        .price(5.00)
        .build();
    when(repository.existsById(anyString())).thenReturn(true);
    when(repository.save(any(Product.class))).thenReturn(expectedProduct);

    Product actualProduct = productService.updateProduct(productId, amendProduct);

    assertEquals(productId, actualProduct.getId());
    verify(repository).existsById(anyString());
    verify(repository).save(any(Product.class));
  }

  @Test
  void updateProductTestGivenInvalidId() {
    String productId = "000001";
    ProductDto amendProduct = ProductDto.builder()
        .price(5.00)
        .build();
    when(repository.existsById(anyString())).thenReturn(false);

    assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, amendProduct));
    verify(repository).existsById(anyString());
    verify(repository, never()).save(any(Product.class));
  }

  @Test
  void deleteProduct() {
    String productId = "000001";
    doNothing().when(repository).deleteById(anyString());

    productService.deleteProduct(productId);

    verify(repository).deleteById(anyString());
  }
}