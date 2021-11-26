package com.bartnik.eventstore.execution.aggregate.registry;

import com.bartnik.eventstore.EventStoreException;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@AllArgsConstructor
public class AggregateConstructorDescriptor {

    @NonNull private final Class<?> aggregateClass;
    @NonNull private final Constructor<?> constructor;

    public boolean supportsArgs(final Class<?>... classes) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();

        if (parameterTypes.length != classes.length) {
            return false;
        }

        for (int i=0; i<classes.length; ++i) {
            // TODO this could check inheritance instead of equality
            if (!parameterTypes[i].equals(classes[i])) {
                return false;
            }
        }

        return true;
    }

    public Object newObject(@NonNull final Object... args) {
        try {
            return constructor.newInstance(args);
        } catch(InvocationTargetException|InstantiationException|IllegalAccessException e) {
            throw new EventStoreException("Could not instantiate aggregate", e);
        }
    }
}
