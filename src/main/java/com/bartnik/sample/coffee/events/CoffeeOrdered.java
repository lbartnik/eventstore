package com.bartnik.sample.coffee.events;

import com.bartnik.eventstore.Event;

import lombok.Value;

@Value
public class CoffeeOrdered implements Event {
    String coffeeBrand;
}
