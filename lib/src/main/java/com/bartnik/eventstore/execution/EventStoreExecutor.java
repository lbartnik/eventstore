package com.bartnik.eventstore.execution;

import com.bartnik.eventstore.execution.agent.ExecutionAgent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventStoreExecutor {

    private static final long LOOP_SLEEP_INTERVAL_MILLIS = 250;

    private final List<ExecutionAgent> agents;
    private final List<Thread> threads;
    private boolean shutDown = false;

    public EventStoreExecutor(@NonNull final ExecutionAgent... agents) {
        this(Arrays.asList(agents));
    }

    public EventStoreExecutor(@NonNull final List<ExecutionAgent> agents) {
        this.agents = agents;
        this.threads = agents.stream()
                .map(agent -> new Thread(() -> agent.execute()))
                .collect(Collectors.toList());
    }

    public void shutDown() {
        this.shutDown = true;
    }

    public void run() {
        threads.forEach(Thread::start);

        while(!shutDown) {
            try {
                Thread.sleep(LOOP_SLEEP_INTERVAL_MILLIS);
            } catch(InterruptedException e) {
                shutDown = true;
            }
        }

        threads.forEach(Thread::interrupt);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch(InterruptedException e) {
                // TODO log
            }
        });
    }
}
