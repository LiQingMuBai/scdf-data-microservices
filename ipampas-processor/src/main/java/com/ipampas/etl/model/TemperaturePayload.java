package com.ipampas.etl.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@ToString
public class TemperaturePayload {
  private final LocalDate date;
  private final double value;
  private final Unit unit;
}
