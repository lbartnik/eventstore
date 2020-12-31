package com.bartnik.eventstore;

import com.bartnik.eventstore.exception.EventStoreException;
import com.bartnik.eventstore.state.StateHandler;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * Maintains a sequence of events related to a single entity.
 * 
 * Its main responsibility is to load and replay events in order to rebuild the
 * current state of the entity.
 */
public class SequencedEvents<T extends State> implements SequencedEventsCollection {

    public interface SequencedEventProducer<R extends SequencedEvent> extends Function<Long, R> {
        R apply(Long sequenceNumber);
    }

    private final StateHandler stateHandler;
    private final List<SequencedEvent> eventsInOrder = new ArrayList<>();

    public SequencedEvents(@NonNull final State state) {
        this.stateHandler = StateHandler.from(state);
    }

    @Override
    public long size() {
        return eventsInOrder.size();
    }

    @Override
    public boolean isEmpty() {
        return eventsInOrder.isEmpty();
    }

    @Override
    public SequencedEvent first() {
        return eventsInOrder.get(0);
    }

    @Override
    public SequencedEvent last() {
        return eventsInOrder.get(eventsInOrder.size()-1);
    }

    @Override
    public Iterator<SequencedEvent> iterator() {
        return eventsInOrder.iterator();
    }

    @Override
    public SequencedEvent[] toArray() {
        return eventsInOrder.toArray(new SequencedEvent[0]);
    }

    public void pushEvent(final SequencedEventProducer eventProducer) throws EventStoreException {
        final long lastSequenceNumber = isEmpty() ? -1 : last().getSequenceNumber();
        final SequencedEvent event = eventProducer.apply(lastSequenceNumber + 1);

        if (!isEmpty() && event.getSource() != first().getSource()) {
            throw new EventStoreException(String.format("New event has source (%s) while existing events have (%s)",
                    event.getSource().toString(), first().getSource().toString()));
        }

        stateHandler.apply(event);
        eventsInOrder.add(event);
    }
}
