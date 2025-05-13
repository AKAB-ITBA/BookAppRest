package org.example.bookapprest.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message, String className) {
        super(className + " " + message);
    }

}
