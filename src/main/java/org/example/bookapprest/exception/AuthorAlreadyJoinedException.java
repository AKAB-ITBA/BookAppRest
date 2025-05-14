package org.example.bookapprest.exception;

public class AuthorAlreadyJoinedException extends RuntimeException {
    public AuthorAlreadyJoinedException(String message) {
        super(message);
    }
}
