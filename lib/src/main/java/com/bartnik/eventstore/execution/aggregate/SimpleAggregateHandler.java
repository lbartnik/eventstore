package com.bartnik.eventstore.execution.aggregate;

import com.bartnik.eventstore.EventStoreException;
import com.bartnik.eventstore.execution.aggregate.registry.EventHandlerDescriptor;
import com.bartnik.eventstore.model.Event;
import com.bartnik.eventstore.model.Sequence;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class SimpleAggregateHandler implements AggregateHandler {

    @NonNull private final Object aggregate;
    @NonNull private final Sequence sequence;
    @NonNull private final Map<Class<?>, EventHandlerDescriptor> eventHandlers;

    @Override
    public List<Event> handle(@NonNull final Event event) {
        if (eventHandlers.containsKey(event.getClass())) {
            throw new EventStoreException("Aggregate cannot handle event");
        }

        final EventHandlerDescriptor handler = eventHandlers.get(event.getClass());
        handler.accept(aggregate, event);

        return sequence.newEvents();
    }
}
