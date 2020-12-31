package com.bartnik.eventstore.backend;

import com.bartnik.eventstore.SequencedEvent;
import lombok.Value;

import java.util.UUID;

@Value
public class StoreEntry {
    UUID id;
    long version;
    SequencedEvent [] events;
}
