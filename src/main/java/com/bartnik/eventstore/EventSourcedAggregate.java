package com.bartnik.eventstore;

import com.bartnik.eventstore.state.AggregateEventManager;

import java.util.UUID;

public interface EventSourcedAggregate {

    UUID getId();

    long getVersion();

    AggregateEventManager getEventManager();
}
