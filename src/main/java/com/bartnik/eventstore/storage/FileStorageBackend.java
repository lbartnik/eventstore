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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileStorageBackend extends AbstractStorageBackend {

    public FileStorageBackend(final SerializationStrategy serializationStrategy) {
        super(serializationStrategy);
    }

    @Override
    public void save(@NonNull final SequencedEventsCollection sequencedEvents) throws EventStoreError {
        final Path path = toPath(sequencedEvents.first().getSource());
        
        if (path.toFile().exists()) {
            final StoreEntry se = readEntry(path);
            // TODO verify optimistic locking
        }

        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(id + ".json", true))) {
            final StoreEntry entry = new StoreEntry(sequencedEvents.first().getSource(), sequencedEvents.first().getSequenceNumber(), sequencedEvents.toArray());
            serializationStrategy.serialize(writer, entry);
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }

    @Override
    public SequencedEvent[] load(@NonNull final UUID id) throws EventStoreError {
        return readEntry(toPath(id)).getEvents();
    }

    private StoreEntry readEntry(final Path path) throws EventStoreError {
        try(final BufferedReader reader = new BufferedReader(new FileReader(path.toFile(), StandardCharsets.UTF_8))) {
            return serializationStrategy.deserialize(reader, StoreEntry.class);
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }

    private static Path toPath(final UUID entryId) {
        return Paths.get(entryId.toString() + ".json");
    }
}