package com.bartnik.eventstore.execution.agent.events;

import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class AutoEventHandler implements EventHandler {
    
    @Override
    public void handle(@NonNull final Event event) {
        // TODO
        //   1. instantiate aggregate
        //   2. pass event to aggregate
        //   3. persist aggregate's new state
        //   4. post new events, if any
    }
}
