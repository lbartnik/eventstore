package com.bartnik.eventstore.registry.aggregate;

import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AggregateRegistry {
    private final List<AggregateDefinition> aggregates;
}
