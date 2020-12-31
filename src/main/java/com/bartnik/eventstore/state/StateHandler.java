package com.bartnik.eventstore.state;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.State;

import com.bartnik.eventstore.exception.StateHandlerError;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StateHandler {

  private final @NonNull State state;
  private final @NonNull Map<Class<? extends SequencedEvent>, Method> handlers;

  public static StateHandler from(final State state) {
    return from(state, new HandlerExtractor());
  }

  private static StateHandler from(final State state, final HandlerExtractor extractor) {
    return new StateHandler(state, extractor.extract(state));
  }

  public void apply(@NonNull final SequencedEvent event) throws StateHandlerError {
    try {
      handlers.get(event.getClass()).invoke(this.state, event);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new StateHandlerError("Could not apply event to state", e);
    }
  }
  
}
