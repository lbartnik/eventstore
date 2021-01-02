package com.bartnik.eventstore.state;

import java.util.UUID;

/**
 * Implemented by classes representing the flattened (projected) state of an aggregate.
 */
public interface AggregateStateAccumulator {

    /**
     * The ID of the parent aggregate.
     * @return
     */
    UUID getId();

    /**
     * The version of the aggregate.
     * @return
     */
    long getVersion();
}
