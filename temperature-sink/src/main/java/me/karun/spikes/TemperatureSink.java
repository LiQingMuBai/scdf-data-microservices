package me.karun.spikes;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


@EnableBinding(Sink.class)
public class TemperatureSink {

  @StreamListener(Sink.INPUT)
  public void loggerSink(Object msg) {
    System.out.println("logging-sink Received: " + msg);
  }
}
