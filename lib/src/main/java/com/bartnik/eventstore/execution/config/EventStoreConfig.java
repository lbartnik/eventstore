package com.bartnik.eventstore.execution.config;

import com.bartnik.eventstore.execution.EventStoreExecutorBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Injects default implementation of all beans necessary to run an EventStore
 * execution agent.
 */
@Configuration
public class EventStoreConfig {
    
    @Bean
    public EventStoreExecutorBuilder eventStoreAgentFactory() {
        return null;
    }
}
