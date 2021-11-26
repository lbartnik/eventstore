package com.bartnik.eventstore.execution;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class EventStoreExecutor {

    @NonNull private final List<ExecutionAgent> agents;

    public void run() {
        // TODO
        //   1. poll all agents for tasks
        //   2. execute tasks in a thread pool
        //   3. use async API (Futures, etc.)
    }

}
