package com.koutsios.reactivemigrationexample.repository;

import com.koutsios.reactivemigrationexample.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
