package com.bartnik.eventstore;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@NoArgsConstructor
public class EventArrayList extends ArrayList<SequencedEvent> implements EventCollection {

    public EventArrayList(final Collection<SequencedEvent> events) {
        super(events);
    }

    public static EventArrayList from(final SequencedEvent [] events) {
        return new EventArrayList(Arrays.asList(events));
    }

    public EventArrayList subList(int from, int to) {
        return new EventArrayList(super.subList(from, to));
    }

    @Override
    public SequencedEvent first() {
        return get(0);
    }

    @Override
    public SequencedEvent[] toArray() {
        return toArray(new SequencedEvent[0]);
    }
}
