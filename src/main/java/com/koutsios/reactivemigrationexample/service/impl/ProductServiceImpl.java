package com.koutsios.reactivemigrationexample.service.impl;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import com.koutsios.reactivemigrationexample.service.ProductService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Override
  public Product getProduct(String id) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<Product> getAllProducts() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Product createProduct(ProductDto newProduct) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Product updateProduct(String id, ProductDto newProduct) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void deleteProduct(String id) {
    throw new UnsupportedOperationException();
  }
}
