package com.bartnik.eventstore.execution;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.EventListenerFactory;
import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.communication.MultiEventListener;
import com.bartnik.eventstore.execution.agent.ExecutionAgent;
import com.bartnik.eventstore.execution.agent.ExecutionTaskFactory;
import com.bartnik.eventstore.execution.agent.SimpleExecutionAgent;
import com.bartnik.eventstore.execution.agent.SimpleExecutionTaskFactory;
import com.bartnik.eventstore.execution.aggregate.AggregateRepository;
import com.bartnik.eventstore.execution.aggregate.SimpleAggregateRepository;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateDescriptor;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistry;
import com.bartnik.eventstore.execution.utils.SimpleAggregateIdentifierPolicy;
import com.bartnik.eventstore.persistence.EventRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EventStoreExecutorFactory {
    
    @NonNull private final EventListenerFactory eventListenerFactory;
    @NonNull private final EventRepository eventRepository;
    @NonNull private final EventPublisher eventPublisher;

    public EventStoreExecutor fromAggregateRegistry(@NonNull final AggregateRegistry registry) {

        // TODO
        //  1. for each aggregate descriptor, instantiate:
        //     * an event subscription for each handler method
        //     * a routing event listener with all those subscriptions
        //     * an aggregate repository
        //     * an execution agent combining the listener and the repository
        //  2. for each execution agent, create a Bean which handles polling

        // what about thread pools for those executors to handle events in parallel?
        // how to differentiate between Lambda and ECS/EC2-type execution (triggered by event vs. polling) ?

        final List<ExecutionAgent> agents = registry.getAggregates().stream()
            .map(this::toEventHandlerAgent)
            .collect(Collectors.toList());

        return new EventStoreExecutor(agents);
    }

    protected ExecutionAgent toEventHandlerAgent(final AggregateDescriptor descriptor) {
        final EventListener listener = createEventListener(descriptor);
        final AggregateRepository aggregateRepository = new SimpleAggregateRepository(eventRepository, descriptor);
        final ExecutionTaskFactory taskFactory = new SimpleExecutionTaskFactory(new SimpleAggregateIdentifierPolicy(), aggregateRepository, eventPublisher);
        return new SimpleExecutionAgent(listener, taskFactory);
    }

    protected EventListener createEventListener(final AggregateDescriptor descriptor) {
        final List<EventListener> listeners = descriptor.getEventHandlers().values().stream()
            .map(eventListenerFactory::forEvent)
            .collect(Collectors.toList());
        return new MultiEventListener(listeners);
    }
}
