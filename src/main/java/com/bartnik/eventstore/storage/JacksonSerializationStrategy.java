package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.exception.SerializationError;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class JacksonSerializationStrategy implements SerializationStrategy {

    private static final ObjectMapper OBJECT_MAPPER = defaultObjectMapper();

    public void serialize(@NonNull final BufferedWriter output, @NonNull final Object value) throws SerializationError {
        try {
            OBJECT_MAPPER.writeValue(output, value);
        } catch (IOException e) {
            throw new SerializationError("Failed to serialize object", e);
        }
    }

    public <T> T deserialize(@NonNull final BufferedReader input, @NonNull final Class<T> template) throws SerializationError {
        try {
            return OBJECT_MAPPER.readValue(input, template);
        } catch (IOException e) {
            throw new SerializationError("Failed to deserialize object", e);
        }
    }

    /**
     * Object mapper which includes type information as the @type attribute.
     *
     * https://cowtowncoder.medium.com/jackson-2-10-safe-default-typing-2d018f0ce2ba
     * @return
     */
    private static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .activateDefaultTyping(
                        BasicPolymorphicTypeValidator.builder()
                                .allowIfBaseType(SequencedEvent.class)
                                .build(),
                        ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE,
                        JsonTypeInfo.As.WRAPPER_ARRAY
                );
    }
}
