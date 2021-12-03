package com.bartnik.eventstore.inmemory.persistence;

import com.bartnik.eventstore.model.Sequence;
import com.bartnik.eventstore.persistence.EventRepository;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryEventRepository implements EventRepository {

    private final Map<UUID, Sequence> store = new HashMap<>();

    @Override
    public Sequence fromId(@NonNull final UUID id) {
        return store.get(id);
    }
}
