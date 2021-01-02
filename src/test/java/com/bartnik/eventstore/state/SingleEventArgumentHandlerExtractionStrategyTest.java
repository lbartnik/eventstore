package com.bartnik.eventstore.state;

import com.bartnik.eventstore.AbstractSequencedEvent;
import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.state.handlers.SingleEventArgumentHandlerExtractionStrategy;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SingleEventArgumentHandlerExtractionStrategyTest {
  
  public static interface TestInterface {}

  public static class TestEvent extends AbstractSequencedEvent {
    public TestEvent(final UUID source, final long seq) {
      super(source, seq);
    }
  }

  public static class TestAggregateStateAccumulator extends AbstractAggregateStateAccumulator {

    public TestAggregateStateAccumulator() {
      super(UUID.randomUUID());
    }

    public void handler(final TestEvent event) {}
    public void someMethod(int a) {}
    public void otherMethod(int a, long b) {}
  }

  private final TestAggregateStateAccumulator state = new TestAggregateStateAccumulator();
  private final SingleEventArgumentHandlerExtractionStrategy extractor = new SingleEventArgumentHandlerExtractionStrategy();

  @Test
  public void identifiesImplementedInterface() {
    assertTrue(extractor.implementsInterface(TestEvent.class, SequencedEvent.class));
    assertFalse(extractor.implementsInterface(TestEvent.class, TestInterface.class));
  }

  @Test
  public void identifiesHandlers() {
    assertEquals(extractor.extract(state).size(), 1);
    assertTrue(extractor.extract(state).keySet().contains(TestEvent.class));
  }

}
