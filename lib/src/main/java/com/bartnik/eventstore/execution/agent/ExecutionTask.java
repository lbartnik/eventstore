package com.bartnik.eventstore.execution.agent;

/**
 * Execution task wraps a handling of an event by an aggregate.
 */
public interface ExecutionTask {

    /**
     * Execute the handling of an event.
     */
    void execute();
}
