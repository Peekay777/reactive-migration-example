package com.koutsios.reactivemigrationexample.fixture;

import com.koutsios.reactivemigrationexample.domain.Product;
import com.koutsios.reactivemigrationexample.dto.ProductDto;
import reactor.core.publisher.Mono;

public class ProductFixture {

  public static ProductDto aProductDto() {
    return ProductDto.builder()
        .name("Apple")
        .description("The round fruit of a tree of the rose family")
        .price(10.00)
        .build();
  }

  public static Product anApple() {
    return Product.builder()
        .id("000001")
        .name("Apple")
        .description("The round fruit of a tree of the rose family")
        .price(10.00)
        .build();
  }

  public static Product aBanana() {
    return Product.builder()
        .id("000002")
        .name("Banana")
        .description("a long curved fruit which grows in clusters and has soft pulpy flesh and " +
            "yellow skin when ripe")
        .price(15.50)
        .build();
  }
}
