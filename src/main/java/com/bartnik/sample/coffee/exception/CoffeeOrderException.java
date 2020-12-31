package com.bartnik.sample.coffee.exception;

public class CoffeeOrderException extends Exception {

    public CoffeeOrderException() {}

    public CoffeeOrderException(final String message) {
        super(message);
    }

    public CoffeeOrderException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
