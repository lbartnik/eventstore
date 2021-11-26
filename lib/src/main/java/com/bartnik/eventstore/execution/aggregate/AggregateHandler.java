package com.bartnik.eventstore.execution.aggregate;

import com.bartnik.eventstore.model.Event;

import java.util.List;

/**
 * Simplifies access to API of an aggregate.
 */
public interface AggregateHandler {

    /**
     * Route event to the correct handler method of an aggregate.
     *
     * @param event Event expected by the aggregate.
     * @return Events produced in a response to handling {@code event}.
     */
    List<Event> handle(Event event);
}
