package com.bartnik.eventstore.state;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.exception.EventStoreError;

import java.util.Optional;
import java.util.function.Function;

/**
 * Aggregate Event manager implements the part of logic shared by all
 * Event-Sourced aggregates.
 *
 * Responsibilities:
 *
 * <ul>
 *     <li>Store all events describing the state of an aggregate</li>
 *     <li>Keep track of new events, which need to be persisted and announced</li>
 *     <li>Keep track of the last persisted aggregate version, necessary to implement optimistic locking</li>
 * </ul>
 */
public interface AggregateEventManager {

    /**
     * Are there any events registered in the manager.
     * @return
     */
    boolean isEmpty();

    /**
     * The current version of the state.
     * @return
     */
    long getVersion();

    /**
     * The order number of the last known persisted version of the aggregate.
     * @return
     */
    Optional<Long> getReferenceVersion();

    /**
     * Access all known events.
     * @return
     */
    EventCollection getAllEvents();

    /**
     * Access only events which haven't been persisted yet.
     * @return
     */
    EventCollection getPendingEvents();

    /**
     * Functional interface used to add new events. This is how Aggregate can
     * create versioned and sequenced events sourcing the next available sequence
     * number from the Event Manager.
     *
     * @param <R>
     */
    interface SequencedEventProducer<R extends SequencedEvent> extends Function<Long, R> {

        /**
         * Create the new event using the next available sequence number.
         *
         * @param nextSequenceNumber Sequence number for the new event to be added to the aggregate.
         * @return
         */
        R apply(Long nextSequenceNumber);
    }

    /**
     * Add a new event to the sequence.
     *
     * @param eventProducer Lambda which creates the event.
     * @throws EventStoreError
     */
    void addEvent(final AbstractAggregateEventManager.SequencedEventProducer<?> eventProducer) throws EventStoreError;
}
