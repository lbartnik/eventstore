package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.execution.agent.events.EventHandler;
import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EventHandlerAgent {
 
    @NonNull private final EventListener eventListener;
    @NonNull private final EventHandler eventHandler;

    public EventTask poll() {

        // TODO run multiple threads
        final Event event = eventListener.poll();

        return () -> {
            eventHandler.handle(event);
            eventListener.processed(event);
        };
    }
}
