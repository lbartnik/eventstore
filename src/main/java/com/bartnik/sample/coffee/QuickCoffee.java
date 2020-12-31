package com.bartnik.sample.coffee;

import java.util.UUID;

import com.bartnik.sample.coffee.domain.CoffeeOrder;
import com.bartnik.sample.coffee.exception.CoffeeOrderException;
import com.bartnik.sample.coffee.exception.CoffeeOrderRepositoryException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QuickCoffee extends MakingCoffee {

  public static void main(final String [] args) {

    log.info("Running QuickCoffee");
    final CoffeeOrder coffeeOrder = repository.create(UUID.randomUUID());

    try {
      coffeeOrder.order("Little Brother");
      coffeeOrder.grindCoffee(50);
      coffeeOrder.brew(100);
    } catch (CoffeeOrderException e) {
      log.error("Could not execute one of the aggregate methods, retry is unlikely to help", e);
    }

    log.info("Saving aggregate to {}.json", coffeeOrder.getId());
    try {
      repository.save(coffeeOrder);
    } catch(CoffeeOrderRepositoryException e) {
      log.error("Could not save the aggregate, retry might help; reload the aggregate and try again", e);
    }

    log.info("Loading aggregate from {}.json", coffeeOrder.getId());
    try {
      repository.load(coffeeOrder.getId());
    } catch(CoffeeOrderRepositoryException e) {
      log.error("Could not save the aggregate, retry might help; reload the aggregate and try again", e);
    }
  }

}
