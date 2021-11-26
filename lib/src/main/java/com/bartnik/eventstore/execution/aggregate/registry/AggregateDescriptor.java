package com.bartnik.eventstore.execution.aggregate.registry;

import com.bartnik.eventstore.EventStoreException;
import com.bartnik.eventstore.model.Sequence;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * Describes an aggregate: its class and the choice of its event handlers.
 */
@Value @AllArgsConstructor
public class AggregateDescriptor {
    
    @NonNull private final Class<?> aggregateClass;
    @NonNull private final List<AggregateConstructorDescriptor> constructors;
    @NonNull private final Map<Class<?>, EventHandlerDescriptor> eventHandlers;

    public Object create(@NonNull final Sequence sequence) {
        final AggregateConstructorDescriptor constructor = constructors.stream()
                .filter(c -> c.supportsArgs(Sequence.class))
                .findFirst()
                .orElseThrow(() -> new EventStoreException("Could not find constructor which takes a Sequence"));

        return constructor.newObject(sequence);
    }
}
