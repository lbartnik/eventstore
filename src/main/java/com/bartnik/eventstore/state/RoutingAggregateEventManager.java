package com.bartnik.eventstore.state;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.exception.EventStoreError;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * State manager which routes all incoming event to the appropriate
 * handler of the state accumulator.
 *
 * Its main responsibility is to load and replay events in order to rebuild the
 * current state of the entity.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class RoutingAggregateEventManager extends AbstractAggregateEventManager {

    /**
     * Used to add incoming events to the aggregate state accumulator.
     */
    private final EventHandlerRouter eventHandlerRouter;

    /**
     * Factory method which creates a new state manager with an empty
     * state.
     *
     * @param accumulator
     * @return
     */
    public static RoutingAggregateEventManager empty(@NonNull final AggregateStateAccumulator accumulator) {
        return new RoutingAggregateEventManager(EventHandlerRouter.from(accumulator));
    }

    /**
     * Factory method which creates a new state manager with a non-empty
     * state by replaying past events.
     *
     * @param accumulator
     * @param events
     * @return
     * @throws EventStoreError
     */
    public static RoutingAggregateEventManager withReplay(@NonNull final AggregateStateAccumulator accumulator, @NonNull final EventCollection events) throws EventStoreError {
        final RoutingAggregateEventManager sm = new RoutingAggregateEventManager(EventHandlerRouter.from(accumulator));

        for(final SequencedEvent event : events) {
            try {
                sm.eventHandlerRouter.apply(event);
                sm.eventsInOrder.add(event);
            } catch (EventStoreError e) {
                throw new EventStoreError(String.format("Could not replay event number %d", event.getSequenceNumber()), e);
            }
        }

        sm.lastPersistedEvent = Optional.of(sm.eventsInOrder.size());
        return sm;
    }

    public void addEvent(final SequencedEventProducer<?> eventProducer) throws EventStoreError {
        final long lastSequenceNumber = isEmpty() ? -1 : last().getSequenceNumber();
        final SequencedEvent event = eventProducer.apply(lastSequenceNumber + 1);

        if (!isEmpty() && event.getSource() != last().getSource()) {
            throw new EventStoreError(String.format("New event has source (%s) while existing events have (%s)",
                    event.getSource().toString(), last().getSource().toString()));
        }

        eventHandlerRouter.apply(event);
        eventsInOrder.add(event);
    }
}
