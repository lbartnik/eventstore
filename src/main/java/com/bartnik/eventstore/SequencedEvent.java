package com.bartnik.eventstore;

import java.util.UUID;

/**
 * Implemented by all events processed in SequencedEvent Store.
 *
 * Provides two basic information about any event: the ID of its parent
 * aggregate and this event's order number in the sequence of events.
 */
public interface SequencedEvent {

    /**
     * Identifier of the parent aggregate.
     * @return
     */
    UUID getSource();

    /**
     * This event's order number in the sequence of events.
     * @return
     */
    long getSequenceNumber();
}
