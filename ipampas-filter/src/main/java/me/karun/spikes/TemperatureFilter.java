package me.karun.spikes;

import me.karun.spikes.model.FilterConfig;
import me.karun.spikes.model.TemperaturePayload;
import me.karun.spikes.repository.FilterConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.Filter;

import javax.annotation.PostConstruct;

import static me.karun.spikes.model.Unit.CELSIUS;
import static me.karun.spikes.model.Unit.FAHRENHEIT;

@EnableBinding(Processor.class)
public class TemperatureFilter {

  private final FilterConfigRepository repository;

  @Autowired
  public TemperatureFilter(final FilterConfigRepository repository) {
    this.repository = repository;
  }

  @PostConstruct
  public void init() {
    repository.save(new FilterConfig(CELSIUS, 35));
    repository.save(new FilterConfig(FAHRENHEIT, 30));
  }

  @Filter(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
  public boolean filterValues(final TemperaturePayload payload) {
    System.out.println("payload = " + payload);

    final FilterConfig filterConfig = repository.findByUnit(payload.getUnit());
    System.out.println("filterConfig = " + filterConfig);

    return payload.getValue() >= filterConfig.getFilterValue();
  }
}
