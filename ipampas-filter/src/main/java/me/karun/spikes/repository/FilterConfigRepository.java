package me.karun.spikes.repository;

import me.karun.spikes.model.FilterConfig;
import me.karun.spikes.model.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilterConfigRepository extends MongoRepository<FilterConfig, String> {
  FilterConfig findByUnit(final Unit unit);
}
