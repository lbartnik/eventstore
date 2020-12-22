package com.bartnik.sample.exception;

public class InvalidOperationException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public InvalidOperationException() {
  }

  public InvalidOperationException(final String message) {
    super(message);
  }
}
