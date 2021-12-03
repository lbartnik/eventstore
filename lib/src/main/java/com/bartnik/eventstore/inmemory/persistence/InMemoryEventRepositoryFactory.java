package com.bartnik.eventstore.inmemory.persistence;

import com.bartnik.eventstore.persistence.EventRepository;
import com.bartnik.eventstore.persistence.EventRepositoryFactory;

public class InMemoryEventRepositoryFactory  implements EventRepositoryFactory {

    @Override
    public EventRepository createEventRepository() {
        return new InMemoryEventRepository();
    }
}
