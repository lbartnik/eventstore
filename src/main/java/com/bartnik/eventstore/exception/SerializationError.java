package com.bartnik.eventstore.exception;

public class SerializationError extends EventStoreError {
    public SerializationError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
