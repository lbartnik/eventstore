package com.bartnik.eventstore.execution;

import com.bartnik.eventstore.EventStoreException;
import com.bartnik.eventstore.communication.CommunicationChannelFactory;
import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.MultiEventListener;
import com.bartnik.eventstore.execution.agent.ExecutionAgent;
import com.bartnik.eventstore.execution.agent.ExecutionTaskFactory;
import com.bartnik.eventstore.execution.agent.SimpleExecutionAgent;
import com.bartnik.eventstore.execution.agent.SimpleExecutionTaskFactory;
import com.bartnik.eventstore.execution.aggregate.AggregateRepository;
import com.bartnik.eventstore.execution.aggregate.SimpleAggregateRepository;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateDescriptor;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistry;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistryBuilder;
import com.bartnik.eventstore.execution.utils.SimpleAggregateIdentifierPolicy;
import com.bartnik.eventstore.inmemory.communication.InMemoryCommunicationChannelFactory;
import com.bartnik.eventstore.inmemory.persistence.InMemoryEventRepositoryFactory;
import com.bartnik.eventstore.persistence.EventRepositoryFactory;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventStoreExecutorBuilder {
    
    private CommunicationChannelFactory communicationChannelFactory = new InMemoryCommunicationChannelFactory();
    private EventRepositoryFactory eventRepositoryFactory = new InMemoryEventRepositoryFactory();
    private Optional<AggregateRegistry> aggregateRegistry = Optional.empty();
    private AggregateRegistryBuilder aggregateRegistryBuilder = AggregateRegistryBuilder.standard();

    private int executionAgentPoolSize = ExecutionAgent.DEFAULT_EXECUTION_POOL_SIZE;


    public static EventStoreExecutorBuilder defaultBuilder() {
        return new EventStoreExecutorBuilder();
    }

    public EventStoreExecutorBuilder withCommunicationChannelFactory(@NonNull final CommunicationChannelFactory communicationChannelFactory) {
        this.communicationChannelFactory = communicationChannelFactory;
        return this;
    }

    public EventStoreExecutorBuilder withEventRepositoryFactory(@NonNull final EventRepositoryFactory eventRepositoryFactory) {
        this.eventRepositoryFactory = eventRepositoryFactory;
        return this;
    }

    public EventStoreExecutorBuilder withAggregateRegistry(@NonNull final AggregateRegistry aggregateRegistry) {
        this.aggregateRegistry = Optional.of(aggregateRegistry);
        return this;
    }

    public EventStoreExecutorBuilder withAggregate(@NonNull final Class<?> aggregate) {
        aggregateRegistryBuilder.withAggregate(aggregate);
        return this;
    }

    public EventStoreExecutorBuilder withExecutionAgentPoolSize(final int executionAgentPoolSize) {
        this.executionAgentPoolSize = executionAgentPoolSize;
        return this;
    }


    public EventStoreExecutor build() {

        if (aggregateRegistry == null) {
            throw new EventStoreException("Cannot build an Event Store Executor without an Aggregate Repository");
        }

        // TODO
        //  1. for each aggregate descriptor, instantiate:
        //     * an event subscription for each handler method
        //     * a routing event listener with all those subscriptions
        //     * an aggregate repository
        //     * an execution agent combining the listener and the repository
        //  2. for each execution agent, create a Bean which handles polling

        // what about thread pools for those executors to handle events in parallel?
        // how to differentiate between Lambda and ECS/EC2-type execution (triggered by event vs. polling) ?

        final List<ExecutionAgent> agents = aggregateRegistry
                .orElseGet(() -> aggregateRegistryBuilder.build())
                .getAggregates().stream()
                .map(this::toEventHandlerAgent)
                .collect(Collectors.toList());

        return new EventStoreExecutor(agents);
    }

    protected ExecutionAgent toEventHandlerAgent(final AggregateDescriptor descriptor) {
        final EventListener listener = createEventListener(descriptor);
        final AggregateRepository aggregateRepository = new SimpleAggregateRepository(eventRepositoryFactory.createEventRepository(), descriptor);
        final ExecutionTaskFactory taskFactory = new SimpleExecutionTaskFactory(new SimpleAggregateIdentifierPolicy(), aggregateRepository, communicationChannelFactory.createEventPublisher());

        return new SimpleExecutionAgent(listener, taskFactory, this.executionAgentPoolSize);
    }

    protected EventListener createEventListener(final AggregateDescriptor descriptor) {
        final List<EventListener> listeners = descriptor.getEventHandlers().values().stream()
            .map(handler -> communicationChannelFactory.createEventListener(handler.getEventClass()))
            .collect(Collectors.toList());
        return new MultiEventListener(listeners);
    }
}
