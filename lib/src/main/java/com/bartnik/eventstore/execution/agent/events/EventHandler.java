package com.bartnik.eventstore.execution.agent.events;

import com.bartnik.eventstore.model.Event;

public interface EventHandler {
    
    void handle(Event event);

}
