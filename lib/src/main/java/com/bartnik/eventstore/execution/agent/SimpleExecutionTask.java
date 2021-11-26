package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.execution.aggregate.AggregateHandler;
import com.bartnik.eventstore.execution.aggregate.AggregateRepository;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SimpleExecutionTask implements ExecutionTask {
    
    @NonNull private final Event externalEvent;
    @NonNull private final AggregateIdentifierPolicy identifierPolicy;
    @NonNull private final AggregateRepository aggregateFactory;
    @NonNull private final EventPublisher eventPublisher;

    @Override
    public void execute() {
        final UUID aggregateId = identifierPolicy.fromEvent(externalEvent);
        final AggregateHandler aggregateHandler = aggregateFactory.createInHandler(aggregateId);

        final List<Event> newEvents = aggregateHandler.handle(externalEvent);
        newEvents.forEach(eventPublisher::post);
    }
}
