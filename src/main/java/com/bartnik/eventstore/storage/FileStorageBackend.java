package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.EventArrayList;
import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.exception.EventStoreError;
import com.bartnik.eventstore.exception.OptimisticLockingError;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

public class FileStorageBackend extends AbstractStorageBackend {

    public FileStorageBackend(final SerializationStrategy serializationStrategy) {
        super(serializationStrategy);
    }

    @Override
    public void save(@NonNull final EventSourcedAggregate aggregate) throws EventStoreError {
        final Path path = toPath(aggregate.getId());
        
        if (path.toFile().exists()) {
            final StoreEntry se = readEntry(path);
            final Optional<Long> reference = aggregate.getEventManager().getReferenceVersion();

            if (!reference.isPresent() || reference.get() != se.version) {
                throw new OptimisticLockingError();
            }
        }

        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile(), false))) {
            serializationStrategy.serialize(writer, toEntry(aggregate));
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }

    @Override
    public EventCollection load(@NonNull final UUID id) throws EventStoreError {
        return EventArrayList.from(readEntry(toPath(id)).getEvents());
    }

    private StoreEntry readEntry(final Path path) throws EventStoreError {
        try(final BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            return serializationStrategy.deserialize(reader, StoreEntry.class);
        } catch (IOException e) {
            throw new EventStoreError("Could not write to file", e);
        }
    }

    private static Path toPath(final UUID entryId) {
        return Paths.get(entryId.toString() + ".json");
    }

    private static StoreEntry toEntry(final EventSourcedAggregate aggregate) {
        return new StoreEntry(aggregate.getId(), aggregate.getVersion(), aggregate.getEventManager().getAllEvents().toArray());
    }
}