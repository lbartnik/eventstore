package com.bartnik.sample.coffee.domain;

import com.bartnik.eventstore.EventCollection;
import com.bartnik.eventstore.state.RoutingAggregateEventManager;
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
    final CoffeeOrder.StateAccumulator accumulator = new CoffeeOrder.StateAccumulator(orderId);
    return new CoffeeOrder(accumulator, RoutingAggregateEventManager.empty(accumulator));
  }

  public CoffeeOrder load(@NonNull final UUID orderId) throws CoffeeOrderRepositoryException {
    final EventCollection events;
    try {
      events = storageBackend.load(orderId);
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not load events", e);
    }

    final CoffeeOrder.StateAccumulator state = new CoffeeOrder.StateAccumulator(orderId);
    try {
      return new CoffeeOrder(state, RoutingAggregateEventManager.withReplay(state, events));
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not replay events", e);
    }
  }

  public void save(@NonNull final CoffeeOrder coffeeOrder) throws CoffeeOrderRepositoryException {
    try {
      storageBackend.save(coffeeOrder);
    } catch (EventStoreError e) {
      throw new CoffeeOrderRepositoryException("Could not save events", e);
    }
  }
}
