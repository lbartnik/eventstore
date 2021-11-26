package com.bartnik.eventstore.communication;

import com.bartnik.eventstore.execution.aggregate.registry.EventHandlerDescriptor;

public interface EventListenerFactory {
    
    EventListener forEvent(EventHandlerDescriptor descriptor);
    
}
