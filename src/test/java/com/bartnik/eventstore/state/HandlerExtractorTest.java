package com.bartnik.eventstore.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bartnik.eventstore.Event;
import com.bartnik.eventstore.State;

import org.junit.jupiter.api.Test;

public class HandlerExtractorTest {
  
  public static interface TestInterface {}

  public static class TestEvent implements Event {}

  public static class TestState implements State {
    public void handler(final TestEvent event) {}
    public void someMethod(int a) {}
    public void otherMethod(int a, long b) {}
  }

  private final TestState state = new TestState();
  private final HandlerExtractor extractor = new HandlerExtractor();

  @Test
  public void identifiesImplementedInterface() {
    assertTrue(extractor.implementsInterface(TestEvent.class, Event.class));
    assertFalse(extractor.implementsInterface(TestEvent.class, TestInterface.class));
  }

  @Test
  public void identifiesHandlers() {
    assertEquals(extractor.extract(state).size(), 1);
    assertTrue(extractor.extract(state).keySet().contains(TestEvent.class));
  }

}
