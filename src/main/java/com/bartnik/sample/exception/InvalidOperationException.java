package com.bartnik.sample.exception;

public class InvalidOperationException extends RuntimeException {

  public InvalidOperationException() {}

  public InvalidOperationException(final String message) {
    super(message);
  }
}
