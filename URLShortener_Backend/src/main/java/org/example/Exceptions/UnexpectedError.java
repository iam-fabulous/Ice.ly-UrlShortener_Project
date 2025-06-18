package org.example.Exceptions;

public class UnexpectedError extends RuntimeException {
    public UnexpectedError(String message) {
        super(message);
    }
}
