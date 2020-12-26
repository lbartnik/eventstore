package com.bartnik.sample.coffee;

import java.util.UUID;

import com.bartnik.sample.coffee.domain.CoffeeOrder;

public class QuickCoffee extends MakingCoffee {
  public static void main(final String [] args) {

    final CoffeeOrder coffeeOrder = repository.create(UUID.randomUUID());
    coffeeOrder.order("Little Brother");
    coffeeOrder.grindCoffee(50);
    coffeeOrder.brew(100);
  }

}
