package com.bartnik.eventstore;

import java.util.UUID;

/**
 * Implemented by classes representing the state of an aggregate.
 */
public interface State {

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
