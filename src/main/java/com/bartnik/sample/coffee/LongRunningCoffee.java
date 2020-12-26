package com.bartnik.sample.coffee;

import java.util.UUID;

import com.bartnik.sample.coffee.domain.CoffeeOrder;

/**
 * Illustrates possible use of Event Store in a scenario where the state
 * needs to be repeatedly written to the storage backend and read from it.
 * 
 * In a real-world scenario, each method called from {@code execute()} would
 * be a command/event handler, responsible for reading the aggregate, routing
 * the event into it and finally writing it back to the storage backend.
 * 
 * Each invocation could take place in a new/independent process and time
 * between invocations can be arbitrarily long. 
 */
public class LongRunningCoffee extends MakingCoffee {
  
  public static void main(final String [] args) {
    new LongRunningCoffee().execute();    
  }

  private void execute() {
    final UUID orderId = UUID.randomUUID();
    orderReceived(orderId, "Little Brother");
    grind(orderId, 50);
    brew(orderId, 100);
  }

  private void orderReceived(final UUID orderId, final String coffeeBrand) {
    final CoffeeOrder coffeeOrder = repository.create(orderId);
    coffeeOrder.order(coffeeBrand);
    repository.save(coffeeOrder);
  }

  private void grind(final UUID orderId, final double weight) {
    final CoffeeOrder coffeeOrder = repository.load(orderId);
    coffeeOrder.grindCoffee(weight);
    repository.save(coffeeOrder);
  }

  private void brew(final UUID orderId, final double volume) {
    final CoffeeOrder coffeeOrder = repository.load(orderId);
    coffeeOrder.brew(volume);
    repository.save(coffeeOrder);
  }

}
