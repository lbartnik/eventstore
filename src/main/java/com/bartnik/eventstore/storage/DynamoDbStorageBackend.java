package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.exception.EventStoreError;
import lombok.NonNull;

import java.util.UUID;

public class DynamoDbStorageBackend implements StorageBackend {

    @Override
    public void save(@NonNull final EventSourcedAggregate sequencedEvents) {

    }

    @Override
    public EventCollection load(UUID id) throws EventStoreError {
        return null;
    }

}
