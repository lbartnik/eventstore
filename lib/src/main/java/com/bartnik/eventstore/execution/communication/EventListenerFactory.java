package com.bartnik.eventstore.execution.communication;

import com.bartnik.eventstore.registry.aggregate.EventHandlerDescriptor;

public interface EventListenerFactory {
    
    EventListener forEvent(EventHandlerDescriptor descriptor);
    
}
