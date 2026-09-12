package com.library.exception;

public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(String isbn) {
        super("Book is currently unavailable: " + isbn);
    }
}