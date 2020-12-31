package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.exception.SerializationError;

import java.io.BufferedReader;
import java.io.BufferedWriter;

public interface SerializationStrategy {

    void serialize(BufferedWriter output, Object value) throws SerializationError;

    <T> T deserialize(BufferedReader input, Class<T> template) throws SerializationError;

}
