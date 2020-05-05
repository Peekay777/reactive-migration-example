package com.koutsios.reactivemigrationexample.controller;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductController {

  @Autowired
  private ProductService productService;

  @GetMapping(path = "/products/{id}")
  public Product getProduct(@PathVariable String id) {
    log.info("Get Product {}", id);
    return productService.getProduct(id);
  }

  @GetMapping(path = "/products")
  public List<Product> getAllProducts() {
    log.info("Get all products");
    return productService.getAllProducts();
  }

  @PostMapping(path = "/products")
  public Product createProduct(@RequestBody ProductDto newProduct) {
    log.info("Create new product");
    return productService.createProduct(newProduct);
  }

  @PutMapping(path = "/products/{id}")
  public Product updateProduct(@PathVariable String id, @RequestBody ProductDto newProduct) {
    log.info("Update Product {}", id);
    return productService.updateProduct(id, newProduct);
  }

  @DeleteMapping(path = "/products/{id}")
  public void deleteProduct(@PathVariable String id) {
    log.info("Delete Product {}", id);
    productService.deleteProduct(id);
  }
}
