package com.bartnik.eventstore.execution;

import com.bartnik.eventstore.execution.agent.ExecutionAgent;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

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
