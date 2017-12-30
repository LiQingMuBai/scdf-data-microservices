package com.ipampas.etl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "filters")
@Data
@AllArgsConstructor
public class FilterConfig {

  @Id
  private Unit unit;
  @Field
  private int filterValue;
}
