package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.AbstractSequencedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CoffeeBrewed extends AbstractSequencedEvent {
  double volume;

  public CoffeeBrewed(@NonNull final UUID source, final long sequenceNumber, final double volume) {
    super(source, sequenceNumber);
    this.volume = volume;
  }
}
