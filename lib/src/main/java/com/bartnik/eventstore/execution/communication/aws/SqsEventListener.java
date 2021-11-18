package com.bartnik.eventstore.execution.communication.aws;

import com.bartnik.eventstore.execution.communication.EventListener;
import com.bartnik.eventstore.model.Event;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SqsEventListener implements EventListener {
    
    @NonNull private final String queueName;

    @Override
    public Event poll() {
        return null;
    }

    @Override
    public void processed(@NonNull final Event event) {

    }
}
