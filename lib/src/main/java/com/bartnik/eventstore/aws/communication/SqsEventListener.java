package com.bartnik.eventstore.aws.communication;

import com.bartnik.eventstore.communication.EventListener;
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
