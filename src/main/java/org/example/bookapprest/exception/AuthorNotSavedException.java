package org.example.bookapprest.exception;

public class AuthorNotSavedException extends RuntimeException {
    public AuthorNotSavedException(String message) {
        super(message);
    }
}
