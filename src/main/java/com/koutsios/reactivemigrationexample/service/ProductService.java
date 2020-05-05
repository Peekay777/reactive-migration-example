package com.koutsios.reactivemigrationexample.service;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import java.util.List;

public interface ProductService {

  Product getProduct(String id);

  List<Product> getAllProducts();

  Product createProduct(ProductDto newProduct);

  Product updateProduct(String id, ProductDto amendedProduct);

  void deleteProduct(String id);

}
