package com.bartnik.eventstore.state;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public abstract class AbstractAggregateStateAccumulator implements AggregateStateAccumulator {

    protected final @NonNull UUID id;
    protected long version;

    public AbstractAggregateStateAccumulator(@NonNull final UUID id) {
        this.id = id;
        this.version = -1;
    }
}
