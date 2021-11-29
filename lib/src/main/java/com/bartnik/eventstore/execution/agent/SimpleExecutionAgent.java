package com.bartnik.eventstore.execution.agent;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.model.Event;
import lombok.NonNull;

import java.util.Optional;
import java.util.concurrent.*;

public class SimpleExecutionAgent implements ExecutionAgent {

    public static final long DEFAULT_POLL_TIMEOUT_MILLIS = 150;

    @NonNull private final EventListener eventListener;
    @NonNull private final ExecutionTaskFactory taskFactory;
    @NonNull private final ExecutorService executorService;
    @NonNull private final ExecutorCompletionService<Optional<Event>> completionService;
    @NonNull private final LinkedBlockingQueue<Future<Optional<Event>>> completionQueue;

    private boolean shutDown = false;

    public SimpleExecutionAgent(@NonNull final EventListener eventListener, @NonNull final ExecutionTaskFactory taskFactory) {
        this(eventListener, taskFactory, DEFAULT_EXECUTION_POOL_SIZE);
    }

    public SimpleExecutionAgent(@NonNull final EventListener eventListener, @NonNull final ExecutionTaskFactory taskFactory, final int executionPoolSize) {
        this(eventListener, taskFactory, Executors.newFixedThreadPool(executionPoolSize));
    }

    public SimpleExecutionAgent(@NonNull final EventListener eventListener, @NonNull final ExecutionTaskFactory taskFactory, @NonNull final ExecutorService executorService) {
        this.eventListener = eventListener;
        this.taskFactory = taskFactory;
        this.executorService = executorService;
        this.completionQueue = new LinkedBlockingQueue<>();
        this.completionService = new ExecutorCompletionService<>(this.executorService, this.completionQueue);
    }

    @Override
    public void shutDown() {
        this.executorService.shutdown();
        this.shutDown = true;
    }

    @Override
    public void execute() {
        completionService.submit(listenerTask());

        while (!shutDown) {
            final Future<Optional<Event>> future = poll();

            if (future == null && completionQueue.isEmpty()) {
                // queue is drained
                if (shutDown) {
                    return;
                }
                // TODO if we are not shutting down, something is wrong
            } else {
                final Optional<Event> result = get(future);
                if (!result.isPresent()) {
                    continue;
                }

                if (shutDown) {
                    // TODO send cancellation to communication channel so that event can be picked up by a different agent
                } else {
                    completionService.submit(eventTask(result.get()));
                    completionService.submit(listenerTask());
                }
            }
        }
    }

    private Future<Optional<Event>> poll() {
        try {
            return completionService.poll(DEFAULT_POLL_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            // TODO log
            shutDown();
        }

        return null;
    }

    private static Optional<Event> get(final Future<Optional<Event>> future) {
        while (true) {
            try {
                return future.get();
            } catch (InterruptedException e) {
                // TODO this should not happen, log; go back to get() until we can extract the value from the future
                // TODO is that legal, to keep calling get() after thread has been interrupted?
            } catch (ExecutionException e) {
                // TODO log error which happened inside the task
                return Optional.empty();
            }
        }
    }

    private Callable<Optional<Event>> listenerTask() {
        return () -> Optional.of(eventListener.poll());
    }

    private Callable<Optional<Event>> eventTask(final Event event) {
        return () -> {
            taskFactory.create(event).execute();
            eventListener.processed(event);
            return Optional.empty();
        };
    }
}
