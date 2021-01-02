package com.bartnik.sample.coffee.domain;

import com.bartnik.ddd.AggregateRoot;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.exception.EventStoreError;
import com.bartnik.eventstore.state.AbstractAggregateStateAccumulator;
import com.bartnik.eventstore.state.AggregateEventManager;
import com.bartnik.eventstore.state.RoutingAggregateEventManager;
import com.bartnik.sample.coffee.events.BeansGround;
import com.bartnik.sample.coffee.events.CoffeeBrewed;
import com.bartnik.sample.coffee.events.CoffeeOrdered;
import com.bartnik.sample.coffee.exception.CoffeeOrderException;
import com.bartnik.sample.coffee.exception.InvalidOperationException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Coffee order.
 * 
 * Being an aggregate root, this class is responsible for maintaining
 * the consistency of processing. In the general case, the state doesn't
 * have to be event-sourced and can be instead written to any other
 * database, e.g. a standard SQL storage.
 */
@AggregateRoot
@RequiredArgsConstructor
public class CoffeeOrder implements EventSourcedAggregate {

    private enum OrderState {
        CREATED,
        ORDERED,
        GROUND,
        BREWED
    }

    // TODO should it perform any consistency checks?
    @Getter
    public static class StateAccumulator extends AbstractAggregateStateAccumulator {

        private OrderState state = OrderState.CREATED;
        private String coffeeBrand;
        private double weight;
        private double volume;

        public StateAccumulator(final UUID id) {
            super(id);
        }

        public boolean isInState(final OrderState state) {
            return this.state == state;
        }

        public void onCoffeeOrdered(final CoffeeOrdered event) {
            version = event.getSequenceNumber();
            state = OrderState.ORDERED;
            coffeeBrand = event.getCoffeeBrand();
        }

        public void onBeansGround(final BeansGround event) {
            version = event.getSequenceNumber();
            state = OrderState.GROUND;
            weight = event.getWeight();
        }

        public void onCoffeeBrewed(final CoffeeBrewed event) {
            version = event.getSequenceNumber();
            state = OrderState.BREWED;
            volume = event.getVolume();
        }
    }

    @NonNull private final StateAccumulator accumulator;
    @NonNull private final AggregateEventManager stateManager;

    @Override
    public UUID getId() {
        return accumulator.getId();
    }

    @Override
    public long getVersion() {
        return accumulator.getVersion();
    }

    @Override
    public AggregateEventManager getEventManager() {
        return stateManager;
    }

    // TODO add command object
    public void order(final String coffeeBrand) throws CoffeeOrderException {
        if (!accumulator.isInState(OrderState.CREATED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Coffee %s has been ordered", coffeeBrand));
        pushEvent((seq) -> new CoffeeOrdered(accumulator.getId(), seq, coffeeBrand));
    }

    // TODO add command object
    public void grindCoffee(final double weight) throws CoffeeOrderException {
        if (!accumulator.isInState(OrderState.ORDERED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Grinding %f grams of %s beans", weight, accumulator.getCoffeeBrand()));
        pushEvent((seq) -> new BeansGround(accumulator.getId(), seq, weight));
    }

    // TODO add command object
    public void brew(final double volume) throws CoffeeOrderException {
        if (!accumulator.isInState(OrderState.GROUND)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Brewing %f milliliters of coffee from %f grams of %s coffee",
            volume, accumulator.getWeight(), accumulator.getCoffeeBrand()));
        pushEvent((seq) -> new CoffeeBrewed(accumulator.getId(), seq, volume));
    }


    /**
     * Pushes event onto the list with proper exception handling.
     *
     * @param eventProducer
     * @throws CoffeeOrderException
     */
    private <E extends SequencedEvent> void pushEvent(final RoutingAggregateEventManager.SequencedEventProducer<E> eventProducer) throws CoffeeOrderException {
        try {
            stateManager.addEvent(eventProducer);
        }
        catch (EventStoreError e) {
            throw new CoffeeOrderException("Could not push event", e);
        }
    }
}
