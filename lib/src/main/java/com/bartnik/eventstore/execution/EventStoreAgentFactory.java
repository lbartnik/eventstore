package com.bartnik.eventstore.execution;

import com.bartnik.eventstore.registry.aggregate.AggregateRegistry;

import lombok.NonNull;

public class EventStoreAgentFactory {
    

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

        registry.getAggregates();

        return new EventStoreAgent();
    }


}
