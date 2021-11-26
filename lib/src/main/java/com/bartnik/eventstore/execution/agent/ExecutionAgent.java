package com.bartnik.eventstore.execution.agent;

/**
 * Handles events for a single class of aggregates.
 */
public interface ExecutionAgent {

    /**
     * Enter an execution loop until stopped.
     */
    void execute();
}
