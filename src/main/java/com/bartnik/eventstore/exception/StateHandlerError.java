package com.bartnik.eventstore.exception;

import com.bartnik.eventstore.exception.EventStoreError;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StateHandlerError extends EventStoreError {

  /**
   *
   */
  private static final long serialVersionUID = -8954911018800276191L;

  public StateHandlerError(final String message) {
    super(message);
  }

  public StateHandlerError(final String message, final Throwable cause) {
    super(message, cause);
  }
  
}
