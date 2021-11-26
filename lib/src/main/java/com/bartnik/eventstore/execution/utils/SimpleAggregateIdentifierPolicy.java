package com.bartnik.eventstore.execution.utils;

import com.bartnik.eventstore.model.Event;
import lombok.NonNull;

import java.util.UUID;

public class SimpleAggregateIdentifierPolicy implements AggregateIdentifierPolicy {

    @Override
    public UUID fromEvent(@NonNull final Event event) {
        return null;
    }
}
