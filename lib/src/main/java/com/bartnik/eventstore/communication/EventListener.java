package com.bartnik.eventstore.communication;

import com.bartnik.eventstore.model.Event;

public interface EventListener {

    // TODO this will need to be wrapped in an envelope
    Event poll();

    /**
     * Mark event as processed.
     * 
     * @param event
     */
    void processed(Event event);
}
