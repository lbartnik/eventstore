package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.model.Event;

/**
 * Creates tasks where events are handled.
 */
public interface ExecutionTaskFactory {

    /**
     * Creates a task to handle the given event.
     *
     * @param externalEvent Event to be handled.
     * @return Task.
     */
    SimpleExecutionTask create(Event externalEvent);
}
