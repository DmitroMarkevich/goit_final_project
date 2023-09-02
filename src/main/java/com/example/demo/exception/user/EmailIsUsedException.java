package com.example.demo.exception.user;

public class EmailIsUsedException extends RuntimeException {

    public EmailIsUsedException(String message) {
        super(message);
    }
}
