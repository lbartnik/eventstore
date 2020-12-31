package com.bartnik.sample.coffee.exception;

public class InvalidOperationException extends CoffeeOrderException {

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
