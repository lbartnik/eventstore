package com.bartnik.eventstore.samples;

import com.bartnik.eventstore.execution.config.EventStoreConfig;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistry;
import com.bartnik.eventstore.execution.aggregate.registry.AggregateRegistryBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import({EventStoreConfig.class})
public class ApplicationConfig {
    
    @Bean
    public AggregateRegistry aggregateRegistry() {
        return AggregateRegistryBuilder.standard()
            .withAggregate(MyAggregate.class)
            .build();
    }
}
