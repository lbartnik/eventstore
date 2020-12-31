package com.bartnik.sample.coffee;

import com.bartnik.eventstore.backend.FileStorageBackend;
import com.bartnik.sample.coffee.domain.CoffeeOrderRepository;

/**
 * Sample application illustrating the usage of SequencedEvent Store.
 */
public abstract class MakingCoffee {

  protected static final CoffeeOrderRepository repository = new CoffeeOrderRepository(new FileStorageBackend());

}
