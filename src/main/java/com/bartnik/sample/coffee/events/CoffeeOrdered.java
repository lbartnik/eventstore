package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.AbstractSequencedEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CoffeeOrdered extends AbstractSequencedEvent {
    String coffeeBrand;

    public CoffeeOrdered(final UUID source, final long sequenceNumber, final String coffeeBrand) {
        super(source, sequenceNumber);
        this.coffeeBrand = coffeeBrand;
    }
}
