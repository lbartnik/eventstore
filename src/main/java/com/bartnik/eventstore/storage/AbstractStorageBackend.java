package com.bartnik.eventstore.storage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractStorageBackend implements StorageBackend {
    @NonNull protected final SerializationStrategy serializationStrategy;
}
