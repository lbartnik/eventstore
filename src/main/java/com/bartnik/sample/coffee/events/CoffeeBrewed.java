package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.AbstractSequencedEvent;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CoffeeBrewed extends AbstractSequencedEvent {
  double volume;

  public CoffeeBrewed(@NonNull final UUID source, final long sequenceNumber, final double volume) {
    super(source, sequenceNumber);

    this.volume = volume;
  }
}
