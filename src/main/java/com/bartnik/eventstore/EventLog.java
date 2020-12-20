package com.bartnik.eventstore;

import java.util.ArrayList;
import java.util.List;

/**
 * Maintains a sequence of events related to a single entity.
 * 
 * Its main responsibility is to load and replay events in order to rebuild the
 * current state of the entity.
 */
public class EventLog<T extends State> {
    
    private final T state;
    private final List<Event> events = new ArrayList<>();

    public EventLog(final T state) {
        this.state = state;
    }

    public void push(final Event event) {}
}
