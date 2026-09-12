package com.library.exception;

public class BorrowingLimitExceededException extends RuntimeException {

    public BorrowingLimitExceededException(String userId) {
        super("User has reached the borrowing limit: " + userId);
    }
}