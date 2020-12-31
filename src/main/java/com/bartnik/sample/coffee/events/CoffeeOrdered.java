package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.AbstractSequencedEvent;
import lombok.NonNull;
import lombok.Value;

import java.util.UUID;

@Value
public class CoffeeOrdered extends AbstractSequencedEvent {
    String coffeeBrand;

    public CoffeeOrdered(@NonNull final UUID source, final long sequenceNumber, @NonNull final String coffeeBrand) {
        super(source, sequenceNumber);

        this.coffeeBrand = coffeeBrand;
    }
}
