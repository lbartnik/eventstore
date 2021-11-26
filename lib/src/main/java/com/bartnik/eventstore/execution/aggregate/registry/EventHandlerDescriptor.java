package com.bartnik.eventstore.execution.aggregate.registry;

import com.bartnik.eventstore.EventStoreException;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Value @AllArgsConstructor
public class EventHandlerDescriptor {

    @NonNull private final Class<?> aggregateClass;
    @NonNull private final Method method;
    @NonNull private final Class<? extends Event> eventClass;

    public void accept(@NonNull final Object aggregate, @NonNull final Event event) {
        if (!aggregate.getClass().equals(aggregateClass)) {
            throw new EventStoreException("Aggregate class does not match");
        }

        try {
            method.invoke(aggregate, event);
        } catch(InvocationTargetException|IllegalAccessException e) {
            throw new EventStoreException("Could not pass event to aggregate", e);
        }
    }
}
