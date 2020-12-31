package com.bartnik.eventstore;

public interface SequencedEventsCollection extends Iterable<SequencedEvent> {

    long size();

    boolean isEmpty();

    SequencedEvent first();

    SequencedEvent last();

    SequencedEvent[] toArray();
}
