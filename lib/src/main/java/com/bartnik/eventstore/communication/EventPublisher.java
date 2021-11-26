package com.bartnik.eventstore.communication;

import com.bartnik.eventstore.model.Event;

public interface EventPublisher {

    void post(Event event);

}
