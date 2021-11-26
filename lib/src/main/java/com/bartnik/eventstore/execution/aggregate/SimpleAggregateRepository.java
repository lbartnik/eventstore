package com.bartnik.eventstore.execution.aggregate;

import com.bartnik.eventstore.execution.aggregate.registry.AggregateDescriptor;
import com.bartnik.eventstore.model.Sequence;
import com.bartnik.eventstore.persistence.EventRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
public class SimpleAggregateRepository implements AggregateRepository {

    @NonNull private final EventRepository eventRepository;
    @NonNull private final AggregateDescriptor aggregateDescriptor;

    @Override
    public Object createNew(@NonNull final UUID aggregateId) {
        final Sequence events = eventRepository.fromId(aggregateId);
        return aggregateDescriptor.create(events);
    }

    @Override
    public AggregateHandler createInHandler(@NonNull final UUID aggregateId) {
        final Sequence events = eventRepository.fromId(aggregateId);
        final Object aggregate = aggregateDescriptor.create(events);

        return new SimpleAggregateHandler(aggregate, events, aggregateDescriptor.getEventHandlers());
    }
}
