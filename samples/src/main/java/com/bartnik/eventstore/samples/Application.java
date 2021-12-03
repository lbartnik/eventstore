package com.bartnik.eventstore.samples;

import com.bartnik.eventstore.execution.EventStoreExecutor;
import com.bartnik.eventstore.execution.EventStoreExecutorBuilder;

public class Application {

	public static void main(final String[] args) throws InterruptedException {

		final EventStoreExecutor executor = EventStoreExecutorBuilder.defaultBuilder()
				.withAggregate(MyAggregate.class)
				.build();

		final Thread executorThread = new Thread(() -> executor.run());
		executorThread.start();

		Thread.sleep(10);

		executor.shutDown();
		executorThread.join();
	}
}
