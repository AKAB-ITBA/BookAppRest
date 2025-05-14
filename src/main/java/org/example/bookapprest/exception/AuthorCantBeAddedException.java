package org.example.bookapprest.exception;

public class AuthorCantBeAddedException extends RuntimeException {
    public AuthorCantBeAddedException(String message) {
        super(message);
    }
}
