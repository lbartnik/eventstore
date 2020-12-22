package com.bartnik.eventstore.exception;

import java.lang.reflect.Executable;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EventStoreException extends Exception {
  
  /**
   *
   */
  private static final long serialVersionUID = -684013187796705347L;

  public EventStoreException(final String message) {
    super(message);
  }

  public EventStoreException(final String message, final Throwable cause) {
    super(message, cause);
  }
  
}
