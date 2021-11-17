package com.bartnik.eventstore.registry.aggregate;

import java.lang.reflect.Method;
import java.util.List;

import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

/**
 * Describes an aggregate: its class and the choice of its event handlers.
 */
@Value @AllArgsConstructor
public class AggregateDescriptor {
    
    @Value @AllArgsConstructor
    public static class EventHandlerDescriptor {
        @NonNull private final Class<? extends Event> eventClass;
        @NonNull private final Method method;
    }

    @NonNull private final Class<?> aggregateClass;
    @NonNull private final List<EventHandlerDescriptor> eventHandlers;

}
