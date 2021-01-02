package com.bartnik.eventstore.state;

import com.bartnik.eventstore.SequencedEvent;
import com.bartnik.eventstore.exception.StateHandlerError;
import com.bartnik.eventstore.state.handlers.SingleEventArgumentHandlerExtractionStrategy;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Invokes event handlers on an aggregate state accumulator.s
 */
@RequiredArgsConstructor
public class EventHandlerRouter {

  private final @NonNull AggregateStateAccumulator state;
  private final @NonNull Map<Class<? extends SequencedEvent>, Method> handlers;

  /**
   * Factor method which uses the default handler extraction strategy, {@link SingleEventArgumentHandlerExtractionStrategy}.
   *
   * @param state
   * @return
   */
  public static EventHandlerRouter from(final AggregateStateAccumulator state) {
    return from(state, new SingleEventArgumentHandlerExtractionStrategy());
  }

  private static EventHandlerRouter from(final AggregateStateAccumulator state, final SingleEventArgumentHandlerExtractionStrategy extractor) {
    return new EventHandlerRouter(state, extractor.extract(state));
  }

  /**
   * Invokes the handler method appropriate for the given event.
   *
   * @param event
   * @throws StateHandlerError
   */
  public void apply(@NonNull final SequencedEvent event) throws StateHandlerError {
    try {
      handlers.get(event.getClass()).invoke(this.state, event);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new StateHandlerError("Could not apply event to state", e);
    }
  }
}
