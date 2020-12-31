package com.bartnik.eventstore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
public abstract class AbstractSequencedEvent implements SequencedEvent {

    protected final @NonNull UUID source;
    protected final long sequenceNumber;
}
