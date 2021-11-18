package com.bartnik.eventstore.samples;

import com.bartnik.eventstore.config.EventStoreConfig;
import com.bartnik.eventstore.registry.aggregate.AggregateRegistry;
import com.bartnik.eventstore.registry.aggregate.AggregateRegistryBuilder;

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
