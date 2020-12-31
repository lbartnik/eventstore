package com.bartnik.sample.coffee.domain;

import com.bartnik.ddd.AggregateRoot;
import com.bartnik.eventstore.AbstractState;
import com.bartnik.eventstore.EventSourcedAggregate;
import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.SequencedEvents;
import com.bartnik.eventstore.SequencedEventsCollection;
import com.bartnik.eventstore.exception.EventStoreException;
import com.bartnik.sample.coffee.events.BeansGround;
import com.bartnik.sample.coffee.events.CoffeeBrewed;
import com.bartnik.sample.coffee.events.CoffeeOrdered;
import com.bartnik.sample.coffee.exception.CoffeeOrderException;
import com.bartnik.sample.coffee.exception.InvalidOperationException;
import lombok.Getter;

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
public class CoffeeOrder implements EventSourcedAggregate {

    private enum OrderState {
        CREATED,
        ORDERED,
        GROUND,
        BREWED
    }

    // TODO should it perform any consistency checks?
    @Getter
    public static class CoffeeOrderState extends AbstractState {

        private OrderState state = OrderState.CREATED;
        private String coffeeBrand;
        private double weight;
        private double volume;

        public CoffeeOrderState(final UUID id) {
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

    private final CoffeeOrderState state;
    private final SequencedEvents<CoffeeOrderState> events;

    public CoffeeOrder(final UUID orderId) {
        this.state = new CoffeeOrderState(orderId);
        this.events = new SequencedEvents<>(this.state);
    }

    @Override
    public UUID getId() {
        return state.getId();
    }

    @Override
    public SequencedEventsCollection getSequencedEvents() {
        return events;
    }

    public void order(final String coffeeBrand) throws CoffeeOrderException {
        if (!state.isInState(OrderState.CREATED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Coffee %s has been ordered", coffeeBrand));
        pushEvent((seq) -> new CoffeeOrdered(state.getId(), seq, coffeeBrand));
    }

    public void grindCoffee(final double weight) throws CoffeeOrderException {
        if (!state.isInState(OrderState.ORDERED)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Grinding %f grams of %s beans", weight, state.getCoffeeBrand()));
        pushEvent((seq) -> new BeansGround(state.getId(), seq, weight));
    }

    public void brew(final double volume) throws CoffeeOrderException {
        if (!state.isInState(OrderState.GROUND)) {
            throw new InvalidOperationException();
        }

        System.out.println(String.format("Brewing %f milliliters of coffee from %f grams of %s coffee",
            volume, state.getWeight(), state.getCoffeeBrand()));
        pushEvent((seq) -> new CoffeeBrewed(state.getId(), seq, volume));
    }


    /**
     * Pushes event onto the list with proper exception handling.
     *
     * @param eventProducer
     * @throws CoffeeOrderException
     */
    private <E extends SequencedEvent> void pushEvent(final SequencedEvents.SequencedEventProducer<E> eventProducer) throws CoffeeOrderException {
        try {
            events.pushEvent(eventProducer);
        }
        catch (EventStoreException e) {
            throw new CoffeeOrderException("Could not push event", e);
        }
    }
}
