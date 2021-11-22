package com.bartnik.eventstore.communication.aws;

import java.util.zip.Adler32;
import java.util.zip.Checksum;

import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.EventListenerFactory;
import com.bartnik.eventstore.execution.registry.aggregate.EventHandlerDescriptor;

import lombok.NonNull;

public class SqsEventListenerFactory implements EventListenerFactory {

    @Override
    public EventListener forEvent(@NonNull final EventHandlerDescriptor descriptor) {

        final String aggregateId = checksum(descriptor.getAggregateClass().getName());
        final String eventId = checksum(descriptor.getEventClass().getName());
        final String queueName = String.format("%s-%s", eventId, aggregateId);

        // TODO confirm that the queue is subscribed to the SNS topic for the event

        return new SqsEventListener(queueName);
    }

    private static String checksum(final String text) {
        final Checksum algorithm = new Adler32();
        algorithm.update(text.getBytes());
        return String.format("%xdl", algorithm.getValue());
    }
}
