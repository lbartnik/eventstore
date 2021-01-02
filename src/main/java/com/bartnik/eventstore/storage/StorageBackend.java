package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.exception.EventStoreError;

import java.util.UUID;

public interface StorageBackend {

    void save(EventSourcedAggregate aggregate) throws EventStoreError;

    EventCollection load(UUID id) throws EventStoreError;

}
