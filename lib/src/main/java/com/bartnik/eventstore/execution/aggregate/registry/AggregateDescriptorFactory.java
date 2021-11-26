package com.bartnik.eventstore.execution.aggregate.registry;

import com.bartnik.eventstore.model.Event;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AggregateDescriptorFactory {
    
    private final Class<? extends Event> eventInterface = Event.class;


    public AggregateDescriptor fromClass(@NonNull final Class<?> aggregateClass) {

        // TODO check if any method has the "handler" annotation in which case pick only annotated methods

        // TODO support for tag in the handler annotation to enable various choices of methods in different executors

        final Map<Class<?>, EventHandlerDescriptor> eventHandlers = Stream.of(aggregateClass.getMethods())
            .filter(this::isEventHandler)
            .map(method -> toEventHandlerDescriptor(aggregateClass, method))
            .collect(Collectors.toMap(EventHandlerDescriptor::getEventClass, Function.identity()));

        final List<AggregateConstructorDescriptor> constructors = Stream.of(aggregateClass.getConstructors())
                .map(constructor -> toAggregateConstructorDescriptor(aggregateClass, constructor))
                .collect(Collectors.toList());

        return new AggregateDescriptor(aggregateClass, constructors, eventHandlers);
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

    private EventHandlerDescriptor toEventHandlerDescriptor(final Class<?> aggregateClass, final Method method) {
        final Class<?> argumentClass = method.getParameterTypes()[0];
        final Class<? extends Event> eventClass = argumentClass.asSubclass(eventInterface);
        
        return new EventHandlerDescriptor(aggregateClass, method, eventClass);
    }

    private AggregateConstructorDescriptor toAggregateConstructorDescriptor(final Class<?> aggregateClass, final Constructor<?> constructor) {
        return new AggregateConstructorDescriptor(aggregateClass, constructor);
    }
}
