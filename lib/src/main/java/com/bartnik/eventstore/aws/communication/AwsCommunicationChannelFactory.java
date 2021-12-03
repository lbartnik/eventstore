package com.bartnik.eventstore.aws.communication;

import com.bartnik.eventstore.communication.CommunicationChannelFactory;
import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.EventPublisher;
import com.bartnik.eventstore.model.Event;
import lombok.NonNull;

public class AwsCommunicationChannelFactory implements CommunicationChannelFactory {

    @Override
    public EventListener createEventListener(@NonNull final Class<? extends Event> eventClass) {
        return null;
    }

    @Override
    public EventPublisher createEventPublisher() {
        return null;
    }
}
