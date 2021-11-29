package com.bartnik.eventstore.aws.communication;

import com.bartnik.eventstore.communication.CommunicationChannelFactory;
import com.bartnik.eventstore.communication.EventListener;
import com.bartnik.eventstore.communication.EventPublisher;

public class AwsCommunicationChannelFactory implements CommunicationChannelFactory {

    @Override
    public EventListener createEventListener() {
        return null;
    }

    @Override
    public EventPublisher createEventPublisher() {
        return null;
    }
}
