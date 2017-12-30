package com.ipampas.etl;

import com.ipampas.etl.model.FilterConfig;
import com.ipampas.etl.model.TemperaturePayload;
import com.ipampas.etl.model.Unit;
import com.ipampas.etl.repository.FilterConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Filter;

import javax.annotation.PostConstruct;

@EnableBinding(Processor.class)
public class IPampasilter {

  private final FilterConfigRepository repository;

  @Autowired
  public IPampasilter(final FilterConfigRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  public void init() {
    repository.save(new FilterConfig(Unit.CELSIUS, 35));
    repository.save(new FilterConfig(Unit.FAHRENHEIT, 30));
  }

  @Filter(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
  public boolean filterValues(final TemperaturePayload payload) {
    System.out.println("payload = " + payload);

    final FilterConfig filterConfig = repository.findByUnit(payload.getUnit());
    System.out.println("filterConfig = " + filterConfig);

    return payload.getValue() >= filterConfig.getFilterValue();
  }
}
