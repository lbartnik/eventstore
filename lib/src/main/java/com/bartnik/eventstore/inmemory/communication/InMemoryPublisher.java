package com.bartnik.eventstore.inmemory.communication;

import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@AllArgsConstructor
public class InMemoryPublisher implements EventPublisher {

    @NonNull private final Function<Class<?>, ConcurrentLinkedQueue<Event>> queueSupplier;

    @Override
    public void post(@NonNull final Event event) {
        queueSupplier.apply(event.getClass()).add(event);
    }
}
