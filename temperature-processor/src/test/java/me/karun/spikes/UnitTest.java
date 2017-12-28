package me.karun.spikes;

import org.assertj.core.data.Offset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static me.karun.spikes.model.Unit.CELSIUS;
import static me.karun.spikes.model.Unit.FAHRENHEIT;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

  @Rule
  public SystemOutRule outRule = new SystemOutRule().muteForSuccessfulTests();

  @Test
  public void convertUnits_whenSourceUnitIsCelsius_shouldConvertToFahrenheit() {
    assertThat(CELSIUS.conversion.apply(1d))
      .isEqualTo(33.8);
  }

  @Test
  public void convertUnits_whenSourceUnitIsFahrenheit_shouldConvertToCelsius() {
    assertThat(FAHRENHEIT.conversion.apply(73d))
      .isCloseTo(22.7778, Offset.offset(0.01));
  }
}
