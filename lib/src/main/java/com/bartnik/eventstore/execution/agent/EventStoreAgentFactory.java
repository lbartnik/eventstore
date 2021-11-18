package com.bartnik.eventstore.execution.agent;

import java.util.List;
import java.util.stream.Collectors;

import com.bartnik.eventstore.execution.agent.events.AutoEventHandler;
import com.bartnik.eventstore.execution.communication.EventListener;
import com.bartnik.eventstore.execution.communication.EventListenerFactory;
import com.bartnik.eventstore.execution.communication.MultiEventListener;
import com.bartnik.eventstore.registry.aggregate.AggregateDescriptor;
import com.bartnik.eventstore.registry.aggregate.AggregateRegistry;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EventStoreAgentFactory {
    
    @NonNull private final EventListenerFactory eventListenerFactory;

    public EventStoreAgent fromAggregateRegistry(@NonNull final AggregateRegistry registry) {

        // TODO
        //  1. for each aggregate descriptor, instantiate:
        //     * an event subscription for each handler method
        //     * a routing event listener with all those subscriptions
        //     * an aggregate repository
        //     * an execution agent combining the listener and the repository
        //  2. for each execution agent, create a Bean which handles polling

        // what about thread pools for those executors to handle events in parallel?
        // how to differentiate between Lambda and ECS/EC2-type execution (triggered by event vs. polling) ?

        final List<EventHandlerAgent> agents = registry.getAggregates().stream()
            .map(this::toEventHandlerAgent)
            .collect(Collectors.toList());

        return new EventStoreAgent(agents);
    }

    protected EventHandlerAgent toEventHandlerAgent(final AggregateDescriptor descriptor) {
        final EventListener listener = createEventListener(descriptor);
        return new EventHandlerAgent(listener, new AutoEventHandler());
    }

    protected EventListener createEventListener(final AggregateDescriptor descriptor) {
        final List<EventListener> listeners = descriptor.getEventHandlers().stream()
            .map(eventListenerFactory::forEvent)
            .collect(Collectors.toList());
        return new MultiEventListener(listeners);
    }
}
