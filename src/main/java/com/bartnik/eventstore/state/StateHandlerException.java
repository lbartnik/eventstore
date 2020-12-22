package com.bartnik.eventstore.state;

import com.bartnik.eventstore.exception.EventStoreException;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StateHandlerException extends EventStoreException {

  /**
   *
   */
  private static final long serialVersionUID = -8954911018800276191L;

  public StateHandlerException(final String message) {
    super(message);
  }

  public StateHandlerException(final String message, final Throwable cause) {
    super(message, cause);
  }
  
}
