package com.bartnik.eventstore.execution.aggregate.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AggregateRegistry {

    @NonNull private final List<AggregateDescriptor> aggregates;

    public Collection<AggregateDescriptor> getAggregates() {
        return Collections.unmodifiableCollection(aggregates);
    }
}
