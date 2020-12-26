package com.bartnik.sample.coffee.domain;

import java.util.UUID;

import com.bartnik.ddd.AggregateRoot;
import com.bartnik.eventstore.EventLog;
import com.bartnik.eventstore.State;
import com.bartnik.sample.coffee.events.BeansGround;
import com.bartnik.sample.coffee.events.CoffeeBrewed;
import com.bartnik.sample.coffee.events.CoffeeOrdered;
import com.bartnik.sample.exception.InvalidOperationException;

import lombok.Getter;

/**
 * Coffee order.
 * 
 * Being an aggregate root, this class is responsible for maintaining
 * the consistency of processing. In the general case, the state doesn't
 * have to be event-sourced and can be instead written to any other
 * database, e.g. a standard SQL storage.
 */
@AggregateRoot
public class CoffeeOrder {

    private enum OrderState {
        CREATED,
        ORDERED,
        GROUND,
        BREWED
    }

    // TODO should it perform any consistency checks?
    @Getter
    private static class CoffeeOrderState implements State {

        private OrderState state = OrderState.CREATED;
        private String coffeeBrand;
        private double weight;
        private double volume;

        public boolean isInState(final OrderState state) {
            return this.state == state;
        }

        public void onCoffeeOrdered(final CoffeeOrdered event) {
            state = OrderState.ORDERED;
            coffeeBrand = event.getCoffeeBrand();
        }

        public void onBeansGround(final BeansGround event) {
            state = OrderState.GROUND;
            weight = event.getWeight();
        }

        public void onCoffeeBrewed(final CoffeeBrewed event) {
            state = OrderState.BREWED;
            volume = event.getVolume();
        }
    }

    private final UUID orderId;
    private final CoffeeOrderState state;
    private final EventLog<CoffeeOrderState> eventLog;

    public CoffeeOrder(final UUID orderId) {
        this.orderId = orderId;
        this.state = new CoffeeOrderState();
        this.eventLog = new EventLog<CoffeeOrder.CoffeeOrderState>(this.state);
    }

    public void order(final String coffeeBrand) {
        if (!state.isInState(OrderState.CREATED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Coffee %s has been ordered", coffeeBrand));
        eventLog.push(new CoffeeOrdered(coffeeBrand));
    }

    public void grindCoffee(final double weight) {
        if (!state.isInState(OrderState.ORDERED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Grinding %f grams of %s beans", weight, state.getCoffeeBrand()));
        eventLog.push(new BeansGround(weight));
    }

    public void brew(final double volume) {
        if (!state.isInState(OrderState.GROUND)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Brewing %f milliliters of coffee from %f grams of %s coffee",
            volume, state.getWeight(), state.getCoffeeBrand()));
        eventLog.push(new CoffeeBrewed(volume));
    }
}
