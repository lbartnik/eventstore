package com.bartnik.eventstore.inmemory.communication;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

@AllArgsConstructor
public class InMemoryListener implements EventListener {

    @NonNull private final Function<Class<?>, ConcurrentLinkedQueue<Event>> queueSupplier;
    @NonNull private final Class<?> eventClass;

    @Override
    public Event poll() {
        return queueSupplier.apply(eventClass).poll();
    }

    @Override
    public void processed(Event event) {
        // TODO implement a mechanism of message visibility, similar to SQS
    }
}
