package org.example.bookapprest.exception;

public class LackOfParameterException extends RuntimeException {
    private static final String MESSAGE = "one or both parameters on body missing";

    public LackOfParameterException() {
        super(MESSAGE);
    }
}
