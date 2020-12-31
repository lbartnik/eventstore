package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreError;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class FileStorageBackend extends AbstractStorageBackend {

    public FileStorageBackend(final SerializationStrategy serializationStrategy) {
        super(serializationStrategy);
    }

    @Override
    public void save(@NonNull final SequencedEventsCollection sequencedEvents) throws EventStoreError {
        final String id = sequencedEvents.first().getSource().toString();

        // TODO check if the file exists; if so, perform the version validation

        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(id + ".json", true))) {
            final StoreEntry entry = new StoreEntry(sequencedEvents.first().getSource(), sequencedEvents.first().getSequenceNumber(), sequencedEvents.toArray());
            serializationStrategy.serialize(writer, entry);
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }

    @Override
    public SequencedEvent[] load(@NonNull final UUID id) throws EventStoreError {
        try(final BufferedReader reader = new BufferedReader(new FileReader(id.toString() + ".json"))) {
            final StoreEntry se = serializationStrategy.deserialize(reader, StoreEntry.class);
            return se.getEvents();
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }
}