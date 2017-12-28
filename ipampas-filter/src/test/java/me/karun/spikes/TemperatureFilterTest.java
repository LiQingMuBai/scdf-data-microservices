package me.karun.spikes;

import me.karun.spikes.model.FilterConfig;
import me.karun.spikes.model.TemperaturePayload;
import me.karun.spikes.repository.FilterConfigRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;

import static me.karun.spikes.model.Unit.CELSIUS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TemperatureFilterTest {

  @Mock
  private FilterConfigRepository repository;
  private TemperaturePayload payload;

  @Rule
  public final SystemOutRule systemErrRule = new SystemOutRule().muteForSuccessfulTests();

  @Before
  public void setup() {
    payload = new TemperaturePayload(LocalDate.parse("2017-01-15"), 20, CELSIUS);
  }

  @Test
  public void filterValues_whenValueIsUnderThreshold_shouldReturnFalse() {
    setupThresholdForTest(30);

    assertThat(new IPampasilter(repository).filterValues(payload))
      .isFalse();
  }

  @Test
  public void filterValues_whenValueIsOverThreshold_shouldReturnTrue() {
    setupThresholdForTest(10);

    assertThat(new IPampasilter(repository).filterValues(payload))
      .isTrue();
  }

  @Test
  public void filterValues_whenValueIsAtThreshold_shouldReturnTrue() {
    setupThresholdForTest(20);

    assertThat(new IPampasilter(repository).filterValues(payload))
      .isTrue();
  }

  private void setupThresholdForTest(final int threshold) {
    when(repository.findByUnit(CELSIUS)).thenReturn(new FilterConfig(CELSIUS, threshold));
  }
}
