package com.bartnik.eventstore.samples;

import com.bartnik.eventstore.execution.EventStoreExecutor;
import com.bartnik.eventstore.execution.EventStoreExecutorBuilder;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistry;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistryBuilder;

public class Application {

	public static void main(final String[] args) throws InterruptedException {

		final AggregateRegistry registry = AggregateRegistryBuilder.standard()
				.withAggregate(MyAggregate.class)
				.build();

		final EventStoreExecutor executor = EventStoreExecutorBuilder.defaultBuilder()
				.withAggregateRegistry(registry)
				.build();

		final Thread executorThread = new Thread(() -> executor.run());
		executorThread.start();

		Thread.sleep(10);

		executor.shutDown();
		executorThread.join();
	}
}
