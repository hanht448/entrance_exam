package com.example.demo_user.exception;

public class EmailNotAvailableException extends RuntimeException {
    public EmailNotAvailableException(String message) {
        super(message);
    }
}

