package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.model.Event;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SimpleExecutionAgent implements ExecutionAgent {

    @NonNull private final EventListener eventListener;
    @NonNull private final ExecutionTaskFactory taskFactory;

    public void execute() {
        while (true) {
            final Event event = eventListener.poll();
            final ExecutionTask task = taskFactory.create(event);

            // TODO pass to a thread pool for execution
            task.execute();
        }
    }
}
