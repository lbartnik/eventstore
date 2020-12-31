package com.bartnik.sample.coffee.exception;

public class CoffeeOrderRepositoryException extends Exception {

    public CoffeeOrderRepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
