package com.bartnik.eventstore;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public abstract class AbstractState implements State {

    protected final @NonNull UUID id;
    protected long version;

    public AbstractState(@NonNull final UUID id) {
        this.id = id;
        this.version = -1;
    }
}
