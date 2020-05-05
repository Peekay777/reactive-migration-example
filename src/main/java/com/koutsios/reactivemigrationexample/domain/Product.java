package com.koutsios.reactivemigrationexample.domain;

import com.googlecode.jmapper.annotations.JMap;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  private String id;
  @NonNull @JMap
  private String name;
  @JMap
  private String description;
  @JMap
  private double price;

}
