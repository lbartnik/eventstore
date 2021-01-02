package com.bartnik.eventstore.state.handlers;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.state.AggregateStateAccumulator;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Interface for algorithms which identify event handlers.
 */
public interface HandlerExtractionStrategy {

    /**
     * Extract event handler methods from the state accumulator.
     *
     * @param state
     * @return A map associating event type with its handler method.
     */
    Map<Class<? extends SequencedEvent>, Method> extract(AggregateStateAccumulator state);
}
