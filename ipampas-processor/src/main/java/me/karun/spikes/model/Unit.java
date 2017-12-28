package me.karun.spikes.model;

import java.util.function.Function;

public enum Unit {
  CELSIUS(i -> ((i * 9) / 5.0) + 32), FAHRENHEIT(i -> ((i - 32) * 5) / 9.0);

  public final Function<Double, Double> conversion;
  Unit(final Function<Double, Double> transformer) {
    this.conversion = transformer;
  }
}
