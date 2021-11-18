package com.bartnik.eventstore.execution.agent;

@FunctionalInterface
public interface EventTask {
    
    void execute();

}
