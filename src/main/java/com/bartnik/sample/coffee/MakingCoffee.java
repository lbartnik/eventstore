package com.bartnik.sample.coffee;

import com.bartnik.sample.coffee.domain.CoffeeOrderRepository;

/**
 * Sample application illustrating the usage of Event Store.
 */
public abstract class MakingCoffee {

  protected static final CoffeeOrderRepository repository = new CoffeeOrderRepository();

}
