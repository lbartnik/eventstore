package com.bartnik.eventstore.execution.aggregate.registry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AggregateRegistryBuilder {
    
    private final Set<Class<?>> aggregates = new HashSet<>();

    @NonNull private final AggregateDescriptorFactory aggregateDescriptorFactory;

    public static AggregateRegistryBuilder standard() {
        return new AggregateRegistryBuilder(new AggregateDescriptorFactory());
    }

    public AggregateRegistryBuilder withAggregate(@NonNull Class<?> clazz) {
        aggregates.add(clazz);
        return this;
    }

    public AggregateRegistry build() {
        final List<AggregateDescriptor> descriptors = aggregates.stream()
            .map(aggregateDescriptorFactory::fromClass)
            .collect(Collectors.toList());

        return new AggregateRegistry(descriptors);
    }
}
