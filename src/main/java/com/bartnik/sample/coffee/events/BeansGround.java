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
public class BeansGround extends AbstractSequencedEvent {
  double weight;

  public BeansGround(@NonNull final UUID source, final long seq, final double weight) {
    super(source, seq);
    this.weight = weight;
  }
}
