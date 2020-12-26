package com.bartnik.eventstore;

import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * Maintains a sequence of events related to a single entity.
 * 
 * Its main responsibility is to load and replay events in order to rebuild the
 * current state of the entity.
 */
@RequiredArgsConstructor
public class EventLog<T extends State> {
    
    private final T state;
    private final List<Event> events = new ArrayList<>();

    public void push(final Event event) {}
}
