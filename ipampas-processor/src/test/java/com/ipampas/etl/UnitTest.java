package com.ipampas.etl;

import com.ipampas.etl.model.Unit;
import org.assertj.core.data.Offset;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.assertj.core.api.Assertions.assertThat;

public class UnitTest {

  @Rule
  public SystemOutRule outRule = new SystemOutRule().muteForSuccessfulTests();

  @Test
  public void convertUnits_whenSourceUnitIsCelsius_shouldConvertToFahrenheit() {
    assertThat(Unit.CELSIUS.conversion.apply(1d))
      .isEqualTo(33.8);
  }

  @Test
  public void convertUnits_whenSourceUnitIsFahrenheit_shouldConvertToCelsius() {
    assertThat(Unit.FAHRENHEIT.conversion.apply(73d))
      .isCloseTo(22.7778, Offset.offset(0.01));
  }
}
