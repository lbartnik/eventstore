package com.bartnik.eventstore.execution.utils;

import com.bartnik.eventstore.model.Event;

import java.util.UUID;

public interface AggregateIdentifierPolicy {

    UUID fromEvent(Event event);

}
