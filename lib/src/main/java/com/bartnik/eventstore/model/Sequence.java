package com.bartnik.eventstore.model;

import java.util.List;

/**
 * A sequence of events which define the state of an aggregate.
 */
public interface Sequence {

    /**
     * Returns events appended to the sequence after its construction.
     *
     * @return New events.
     */
    List<Event> newEvents();
}
