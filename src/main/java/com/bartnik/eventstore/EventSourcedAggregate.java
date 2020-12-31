package com.bartnik.eventstore;

import java.util.UUID;

public interface EventSourcedAggregate {

    UUID getId();

    SequencedEventsCollection getSequencedEvents();
}
