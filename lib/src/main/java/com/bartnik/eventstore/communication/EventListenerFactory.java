package com.bartnik.eventstore.communication;

import com.bartnik.eventstore.execution.registry.aggregate.EventHandlerDescriptor;

public interface EventListenerFactory {
    
    EventListener forEvent(EventHandlerDescriptor descriptor);
    
}
