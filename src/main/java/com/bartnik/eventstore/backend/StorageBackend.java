package com.bartnik.eventstore.backend;

import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreException;

public interface StorageBackend {

    void save(SequencedEventsCollection sequencedEvents) throws EventStoreException;

}
