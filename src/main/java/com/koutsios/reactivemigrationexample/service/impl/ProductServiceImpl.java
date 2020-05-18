package com.koutsios.reactivemigrationexample.service.impl;

import static java.lang.Boolean.FALSE;

import com.googlecode.jmapper.JMapper;
import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.exception.ProductNotFoundException;
import com.koutsios.reactivemigrationexample.repository.ProductRepository;
import com.koutsios.reactivemigrationexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository repository;

  private final JMapper<Product, ProductDto> productMapper = new JMapper<>(Product.class,
      ProductDto.class);

  @Override
  public Mono<Product> getProduct(String id) {
    return repository.findById(id)
        .switchIfEmpty(Mono.error(new ProductNotFoundException(id)));
  }

  @Override
  public Flux<Product> getAllProducts() {
    return repository.findAll();
  }

  @Override
  public Mono<Product> createProduct(ProductDto newProduct) {
    return repository.save(productMapper.getDestination(newProduct));
  }

  @Override
  public Mono<Product> updateProduct(String id, ProductDto amendedProduct) {
    return repository.existsById(id)
        .map(productExists -> {
          if (FALSE.equals(productExists)) {
            throw new ProductNotFoundException(id);
          }
          return productExists;
        })
        .then(Mono.just(productMapper.getDestination(amendedProduct)))
        .flatMap(updatedProduct -> {
          updatedProduct.setId(id);
          return repository.save(updatedProduct);
        });
  }

  @Override
  public Mono<Void> deleteProduct(String id) {
    return repository.deleteById(id);
  }

}
