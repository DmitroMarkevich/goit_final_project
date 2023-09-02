package com.example.demo.exception.user;

public class EmailAlreadyUsedException extends Exception {
    private static final String EMAIL_ALREADY_EXIST_EXCEPTION_TEXT = "User with email %s already exist.";

    public EmailAlreadyUsedException(String email) {
        super(String.format(EMAIL_ALREADY_EXIST_EXCEPTION_TEXT, email));
    }
}
