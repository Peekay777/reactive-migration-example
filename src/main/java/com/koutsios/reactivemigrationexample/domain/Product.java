package com.koutsios.reactivemigrationexample.domain;

import com.mongodb.lang.NonNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
public class Product {

  @Id
  private String id;
  @NonNull
  private String name;
  private String description;
  @NonNull
  private double price;

}
