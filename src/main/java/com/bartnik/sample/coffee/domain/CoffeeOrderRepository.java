package com.bartnik.sample.coffee.domain;

import java.util.UUID;

public class CoffeeOrderRepository {

  public CoffeeOrder create(final UUID orderId) {
    return new CoffeeOrder(orderId);
  }

  public CoffeeOrder load(final UUID orderId) {
    final CoffeeOrder workflow = new CoffeeOrder(orderId);

    // create a new EtlWorkflowState
    // use the Store to load all events for this workflow id
    // replay them using EventLog and the state created in line 1

    return workflow;
  }

  public void save(final CoffeeOrder coffeeOrder) {
    // write to storage backend
  }
}
