package com.bartnik.eventstore.execution.aggregate;

import java.util.UUID;

/**
 * Create new aggregate objects.
 */
public interface AggregateRepository {

    /**
     * Create a new aggregate object initialized with existing events.
     *
     * @param aggregateId Aggregate identifier.
     * @return Aggregate object.
     */
    Object createNew(UUID aggregateId);

    /**
     * Create a new aggregate object initialized with existing events and wrap
     * it in a handler object which simplifies event handling API.
     *
     * @param aggregateId Aggregate identifier.
     * @return Aggregate handler object.
     * */
    AggregateHandler createInHandler(UUID aggregateId);
}
