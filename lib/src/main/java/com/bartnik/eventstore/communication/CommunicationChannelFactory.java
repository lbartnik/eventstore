package com.bartnik.eventstore.communication;

public interface CommunicationChannelFactory {

    EventListener createEventListener();

    EventPublisher createEventPublisher();

}
