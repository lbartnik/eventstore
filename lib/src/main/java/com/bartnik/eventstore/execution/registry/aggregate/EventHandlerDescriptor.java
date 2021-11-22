package com.bartnik.eventstore.execution.registry.aggregate;

import java.lang.reflect.Method;

import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@Value @AllArgsConstructor
public class EventHandlerDescriptor {
    @NonNull private final Class<?> aggregateClass;
    @NonNull private final Method method;
    @NonNull private final Class<? extends Event> eventClass;
}
