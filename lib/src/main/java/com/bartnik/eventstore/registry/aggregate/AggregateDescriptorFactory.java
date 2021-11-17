package com.bartnik.eventstore.registry.aggregate;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bartnik.eventstore.model.Event;

import lombok.NonNull;

public class AggregateDescriptorFactory {
    
    private final Class<? extends Event> eventInterface = Event.class;


    public AggregateDescriptor fromClass(@NonNull final Class<?> aggregateClass) {

        // TODO check if any method has the "handler" annotation in which case pick only annotated methods

        // TODO support for tag in the handler annotation to enable various choices of methods in different executors

        final List<AggregateDescriptor.EventHandlerDescriptor> eventHandlers = Stream.of(aggregateClass.getMethods())
            .filter(this::isEventHandler)
            .map(this::toEventHandlerDescriptor)
            .collect(Collectors.toList());

        return new AggregateDescriptor(aggregateClass, eventHandlers);
    }

    private boolean isEventHandler(final Method method) {
        // accepts exactly one argument
        if (method.getParameterTypes().length != 1) {
            return false;
        }

        // argument implements the Event interface
        final Class<?> argumentClass = method.getParameterTypes()[0];
        if (!eventInterface.isAssignableFrom(argumentClass)) {
            return false;
        }

        return true;
    }

    private AggregateDescriptor.EventHandlerDescriptor toEventHandlerDescriptor(final Method method) {
        final Class<?> argumentClass = method.getParameterTypes()[0];
        final Class<? extends Event> eventClass = argumentClass.asSubclass(eventInterface);
        
        return new AggregateDescriptor.EventHandlerDescriptor(eventClass, method);
    }

}
