package com.bartnik.eventstore;

public interface EventCollection extends Iterable<SequencedEvent> {

    SequencedEvent first();

    SequencedEvent [] toArray();
}
