# eventstore

Event Store for Java

## Example

Below is a minimal config necessary to execute an application using `eventstore`:

```Java
@Configuration
public class ApplicationConfig {
    
    @Bean
    public AggregateRegistry aggregateRegistry() {
        return AggregateRegistryBuilder.standard()
            .withAggregate(MyAggregate.class)
            .build();
    }
}
```
