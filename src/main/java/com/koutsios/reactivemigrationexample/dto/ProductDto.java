package com.koutsios.reactivemigrationexample.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDto {

  private final String name;
  private final String description;
  private final double price;

}
