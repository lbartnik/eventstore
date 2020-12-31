package com.bartnik.sample.coffee;

import java.util.UUID;

import com.bartnik.sample.coffee.domain.CoffeeOrder;
import com.bartnik.sample.coffee.exception.CoffeeOrderException;
import com.bartnik.sample.coffee.exception.CoffeeOrderRepositoryException;
import lombok.extern.slf4j.Slf4j;

/**
 * Illustrates possible use of SequencedEvent Store in a scenario where the state
 * needs to be repeatedly written to the storage backend and read from it.
 * 
 * In a real-world scenario, each method called from {@code execute()} would
 * be a command/event handler, responsible for reading the aggregate, routing
 * the event into it and finally writing it back to the storage backend.
 * 
 * Each invocation could take place in a new/independent process and time
 * between invocations can be arbitrarily long. 
 */
@Slf4j
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

    try {
      coffeeOrder.order(coffeeBrand);
      repository.save(coffeeOrder);
    } catch(CoffeeOrderException e) {
      log.error("Could not execute the aggregate method order(), retry is unlikely to help", e);
    } catch(CoffeeOrderRepositoryException e) {
      log.error("Could not save the aggregate, retry might help; reload the aggregate and try again", e);
    }
  }

  private void grind(final UUID orderId, final double weight) {
    final CoffeeOrder coffeeOrder = repository.load(orderId);

    try {
      coffeeOrder.grindCoffee(weight);
      repository.save(coffeeOrder);
    } catch(CoffeeOrderException e) {
      log.error("Could not execute the aggregate method order(), retry is unlikely to help", e);
    } catch(CoffeeOrderRepositoryException e) {
      log.error("Could not save the aggregate, retry might help; reload the aggregate and try again", e);
    }
  }

  private void brew(final UUID orderId, final double volume) {
    final CoffeeOrder coffeeOrder = repository.load(orderId);

    try {
      coffeeOrder.brew(volume);
      repository.save(coffeeOrder);
    } catch(CoffeeOrderException e) {
      log.error("Could not execute the aggregate method order(), retry is unlikely to help", e);
    } catch(CoffeeOrderRepositoryException e) {
      log.error("Could not save the aggregate, retry might help; reload the aggregate and try again", e);
    }
  }

}
