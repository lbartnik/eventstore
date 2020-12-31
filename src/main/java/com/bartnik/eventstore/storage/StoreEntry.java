package com.bartnik.eventstore.storage;

import com.bartnik.eventstore.SequencedEvent;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class StoreEntry {
    UUID id;
    long version;
    SequencedEvent [] events;
}
