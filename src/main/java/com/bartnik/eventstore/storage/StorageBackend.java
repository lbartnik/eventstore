package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreError;

import java.util.UUID;

public interface StorageBackend {

    void save(SequencedEventsCollection sequencedEvents) throws EventStoreError;

    SequencedEvent[] load(UUID id) throws EventStoreError;

}
