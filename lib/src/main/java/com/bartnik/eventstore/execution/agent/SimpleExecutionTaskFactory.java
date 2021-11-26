package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.execution.aggregate.AggregateRepository;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleExecutionTaskFactory implements ExecutionTaskFactory {

    @NonNull private final AggregateIdentifierPolicy identifierPolicy;
    @NonNull private final AggregateRepository aggregateRepository;
    @NonNull private final EventPublisher eventPublisher;

    @Override
    public SimpleExecutionTask create(@NonNull final Event externalEvent) {
        return new SimpleExecutionTask(externalEvent, identifierPolicy, aggregateRepository, eventPublisher);
    }
}
