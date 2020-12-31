package com.bartnik.eventstore.backend;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import lombok.NonNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileStorageBackend implements StorageBackend {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            // see https://cowtowncoder.medium.com/jackson-2-10-safe-default-typing-2d018f0ce2ba
            .activateDefaultTyping(
                    BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(SequencedEvent.class)
                    .build()
            );

    @Override
    public void save(@NonNull final SequencedEventsCollection sequencedEvents) throws EventStoreException {
        final String id = sequencedEvents.first().getSource().toString();

        try(final BufferedWriter writer = new BufferedWriter(new FileWriter(id + ".json", true))) {
            final StoreEntry entry = new StoreEntry(sequencedEvents.first().getSource(), sequencedEvents.first().getSequenceNumber(), sequencedEvents.toArray());
            OBJECT_MAPPER.writeValue(writer, entry);
        } catch (IOException e) {
            throw new EventStoreException("Could not write to file", e);
        }
    }
}