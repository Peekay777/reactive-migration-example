package com.koutsios.reactivemigrationexample.service.impl;

import com.googlecode.jmapper.JMapper;
import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.exception.ProductNotFoundException;
import com.koutsios.reactivemigrationexample.repository.ProductRepository;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository repository;

  private final JMapper<Product, ProductDto> productMapper = new JMapper<>(Product.class,
      ProductDto.class);

  @Override
  public Product getProduct(String id) {
    return repository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  @Override
  public List<Product> getAllProducts() {
    return repository.findAll();
  }

  @Override
  public Product createProduct(ProductDto newProduct) {
    return repository.save(productMapper.getDestination(newProduct));
  }

  @Override
  public Product updateProduct(String id, ProductDto amendedProduct) {
    if (repository.existsById(id)) {
      Product updatedProduct = productMapper.getDestination(amendedProduct);
      updatedProduct.setId(id);
      return repository.save(updatedProduct);
    }
    throw new ProductNotFoundException(id);
  }

  @Override
  public void deleteProduct(String id) {
    repository.deleteById(id);
  }

}
