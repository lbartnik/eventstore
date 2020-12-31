package com.bartnik.eventstore.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventStoreError extends Exception {
  
  /**
   *
   */
  private static final long serialVersionUID = -684013187796705347L;

  public EventStoreError(final String message) {
    super(message);
  }

  public EventStoreError(final String message, final Throwable cause) {
    super(message, cause);
  }
  
}
