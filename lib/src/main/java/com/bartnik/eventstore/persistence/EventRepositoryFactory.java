package com.bartnik.eventstore.persistence;

public interface EventRepositoryFactory {

    EventRepository createEventRepository();

}
