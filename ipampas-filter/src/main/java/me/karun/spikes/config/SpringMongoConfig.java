package me.karun.spikes.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class SpringMongoConfig {

  @Bean
  public MongoDbFactory mongoDbFactory() throws Exception {
    return new SimpleMongoDbFactory(new MongoClient(), "temperature-filter-db");
  }

  @Bean
  public MongoTemplate mongoTemplate(final MongoDbFactory mongoDbFactory) throws Exception {
    return new MongoTemplate(mongoDbFactory);
  }
}
