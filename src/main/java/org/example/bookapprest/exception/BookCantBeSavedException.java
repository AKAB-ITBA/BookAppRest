package org.example.bookapprest.exception;

public class BookCantBeSavedException extends RuntimeException {
    public BookCantBeSavedException(String message) {
        super(message);
    }
}
