package com.bartnik.eventstore.execution.registry.aggregate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * Describes an aggregate: its class and the choice of its event handlers.
 */
@Value @AllArgsConstructor
public class AggregateDescriptor {
    
    @NonNull private final Class<?> aggregateClass;
    @NonNull private final List<EventHandlerDescriptor> eventHandlers;
}
