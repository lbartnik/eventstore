package com.bartnik.eventstore.communication;

import java.util.Arrays;
import java.util.List;

import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class MultiEventListener implements EventListener {
    
    @NonNull private final List<EventListener> listeners;

    public MultiEventListener(@NonNull final EventListener ...listeners) {
        this(Arrays.asList(listeners));
    }

    @Override
    public Event poll() {

        // TODO run all listeners in threads, return the first hit

        return null;
    }

    @Override
    public void processed(Event event) {        
    }
}
