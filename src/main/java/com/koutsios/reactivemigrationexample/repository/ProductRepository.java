package com.koutsios.reactivemigrationexample.repository;

import com.koutsios.reactivemigrationexample.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
