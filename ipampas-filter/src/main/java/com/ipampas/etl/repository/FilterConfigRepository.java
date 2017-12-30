package com.ipampas.etl.repository;

import com.ipampas.etl.model.FilterConfig;
import com.ipampas.etl.model.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilterConfigRepository extends MongoRepository<FilterConfig, String> {
  FilterConfig findByUnit(final Unit unit);
}
