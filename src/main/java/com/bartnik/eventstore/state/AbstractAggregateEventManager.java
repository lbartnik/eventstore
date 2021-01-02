package com.bartnik.eventstore.state;

import com.bartnik.eventstore.EventArrayList;
import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.SequencedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Basic functionality of any aggregate state manager.
 */
public abstract class AbstractAggregateEventManager implements AggregateEventManager {

    /**
     * All events known to the aggregate.
     */
    protected final List<SequencedEvent> eventsInOrder = new ArrayList<>();

    /**
     * Used to distinguish "persisted" events from those that are still "pending".
     */
    protected Optional<Integer> lastPersistedEvent = Optional.empty();

    @Override
    public boolean isEmpty() {
        return eventsInOrder.isEmpty();
    }

    @Override
    public long getVersion() {
        return last().getSequenceNumber();
    }

    @Override
    public Optional<Long> getReferenceVersion() {
        return lastPersistedEvent.map((index) -> eventsInOrder.get(index).getSequenceNumber());
    }

    @Override
    public EventCollection getAllEvents() {
        return new EventArrayList(eventsInOrder);
    }

    @Override
    public EventCollection getPendingEvents() {
        if (!lastPersistedEvent.isPresent()) {
            return new EventArrayList(eventsInOrder);
        }

        return new EventArrayList(eventsInOrder.subList(lastPersistedEvent.get() + 1, eventsInOrder.size()));
    }

    protected SequencedEvent last() {
        return eventsInOrder.get(eventsInOrder.size()-1);
    }
}
