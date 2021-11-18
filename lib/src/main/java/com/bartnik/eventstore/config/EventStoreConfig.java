package com.bartnik.eventstore.config;

import com.bartnik.eventstore.execution.EventStoreAgentFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Injects default implementation of all beans necessary to run an EventStore
 * execution agent.
 */
@Configuration
public class EventStoreConfig {
    
    @Bean
    public EventStoreAgentFactory eventStoreAgentFactory() {
        return null;
    }
}
