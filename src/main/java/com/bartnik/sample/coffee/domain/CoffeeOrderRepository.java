package com.bartnik.sample.coffee.domain;

import com.bartnik.eventstore.backend.StorageBackend;
import com.bartnik.eventstore.exception.EventStoreException;
import com.bartnik.sample.coffee.exception.CoffeeOrderRepositoryException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CoffeeOrderRepository {

  @NonNull private final StorageBackend storageBackend;

  public CoffeeOrder create(final UUID orderId) {
    return new CoffeeOrder(orderId);
  }

  public CoffeeOrder load(final UUID orderId) {
    final CoffeeOrder workflow = new CoffeeOrder(orderId);

    // create a new EtlWorkflowState
    // use the Store to load all events for this workflow id
    // replay them using SequencedEvents and the state created in line 1

    return workflow;
  }

  public void save(final CoffeeOrder coffeeOrder) throws CoffeeOrderRepositoryException {
    try {
      storageBackend.save(coffeeOrder.getSequencedEvents());
    } catch (EventStoreException e) {
      throw new CoffeeOrderRepositoryException("Could not save events", e);
    }
  }
}
