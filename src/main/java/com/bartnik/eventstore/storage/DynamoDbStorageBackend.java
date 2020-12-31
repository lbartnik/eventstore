package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreError;
import lombok.NonNull;

import java.util.UUID;

public class DynamoDbStorageBackend implements StorageBackend {

    @Override
    public void save(@NonNull final SequencedEventsCollection sequencedEvents) {

    }

    @Override
    public SequencedEvent[] load(UUID id) throws EventStoreError {
        return null;
    }

}
