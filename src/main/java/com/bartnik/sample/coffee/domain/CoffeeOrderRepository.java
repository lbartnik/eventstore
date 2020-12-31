package com.bartnik.sample.coffee.domain;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEvents;
import com.bartnik.eventstore.storage.StorageBackend;
import com.bartnik.eventstore.exception.EventStoreError;
import com.bartnik.sample.coffee.exception.CoffeeOrderRepositoryException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CoffeeOrderRepository {

  @NonNull private final StorageBackend storageBackend;

  public CoffeeOrder create(final UUID orderId) {
    final CoffeeOrder.CoffeeOrderState state = new CoffeeOrder.CoffeeOrderState(orderId);
    return new CoffeeOrder(state, new SequencedEvents<>(state));
  }

  public CoffeeOrder load(@NonNull final UUID orderId) throws CoffeeOrderRepositoryException {
    final SequencedEvent[] events;
    try {
      events = storageBackend.load(orderId);
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not load events", e);
    }

    final CoffeeOrder.CoffeeOrderState state = new CoffeeOrder.CoffeeOrderState(orderId);
    try {
      return new CoffeeOrder(state, SequencedEvents.withReplay(state, events));
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not replay events", e);
    }
  }

  public void save(@NonNull final CoffeeOrder coffeeOrder) throws CoffeeOrderRepositoryException {
    try {
      storageBackend.save(coffeeOrder.getSequencedEvents());
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not save events", e);
    }
  }
}
