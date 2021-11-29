package com.bartnik.eventstore.execution.agent;

/**
 * Handles events for a single class of aggregates.
 */
public interface ExecutionAgent {

    int DEFAULT_EXECUTION_POOL_SIZE = 3;

    /**
     * Enter an execution loop until stopped.
     */
    void execute();

    /**
     * Request the shutdown of the execution of the main loop.
     */
    void shutDown();
}
