package com.bartnik.eventstore.persistence;

import com.bartnik.eventstore.model.Sequence;

import java.util.UUID;

public interface EventRepository {
    
    Sequence fromId(UUID id);

}
