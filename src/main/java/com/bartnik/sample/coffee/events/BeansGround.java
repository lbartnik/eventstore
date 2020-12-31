package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.AbstractSequencedEvent;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class BeansGround extends AbstractSequencedEvent {
  final double weight;

  public BeansGround(@NonNull final UUID source, final long seq, final double weight) {
    super(source, seq);

    this.weight = weight;
  }
}
