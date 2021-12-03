package com.bartnik.eventstore.inmemory.communication;

import com.bartnik.eventstore.communication.CommunicationChannelFactory;
import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.model.Event;
import lombok.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class InMemoryCommunicationChannelFactory  implements CommunicationChannelFactory {

    private final ConcurrentMap<Class<?>, ConcurrentLinkedQueue<Event>> channels = new ConcurrentHashMap<>();
    private final Function<Class<?>, ConcurrentLinkedQueue<Event>> queueSupplier =
            (event) -> channels.computeIfAbsent(event.getClass(), (k) -> new ConcurrentLinkedQueue<>());

    @Override
    public EventListener createEventListener(@NonNull final Class<? extends Event> eventClass) {
        return new InMemoryListener(queueSupplier, eventClass);
    }

    @Override
    public EventPublisher createEventPublisher() {
        return new InMemoryPublisher(queueSupplier);
    }
}
