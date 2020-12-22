package com.bartnik.eventstore.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.bartnik.eventstore.Event;
import com.bartnik.eventstore.State;

@RequiredArgsConstructor
public class HandlerExtractor {

  final Class<?> base;

  public HandlerExtractor() {
    this(Event.class);
  }

  public Map<Class<? extends Event>, Method> extract(@NonNull final State state) {
    return List.of(state.getClass().getMethods())
      .stream()
      .filter(this::isEventHandler)
      .collect(Collectors.toMap(this::eventType, Function.identity()));
  }

  public boolean isUnary(final Method method) {
    return method.getParameterTypes().length == 1;
  }

  public Class<?> firstParameterType(final Method method) {
    return method.getParameterTypes()[0];
  }

  public boolean isEventHandler(final Method method) {
    return isUnary(method) && implementsInterface(firstParameterType(method), base);
  }

  public boolean implementsInterface(final Class<?> clazz, final Class<?> interfaze) {
    return interfaze.isAssignableFrom(clazz);
  }

  public Class<? extends Event> eventType(final Method method) {
    return firstParameterType(method).asSubclass(Event.class);
  }

}
