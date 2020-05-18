package com.koutsios.reactivemigrationexample.service;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

  Mono<Product> getProduct(String id);

  Flux<Product> getAllProducts();

  Mono<Product> createProduct(ProductDto newProduct);

  Mono<Product> updateProduct(String id, ProductDto amendedProduct);

  Mono<Void> deleteProduct(String id);

}
