package com.bartnik.eventstore.communication;

import com.bartnik.eventstore.model.Event;

public interface CommunicationChannelFactory {

    EventListener createEventListener(Class<? extends Event> eventClass);

    EventPublisher createEventPublisher();

}
