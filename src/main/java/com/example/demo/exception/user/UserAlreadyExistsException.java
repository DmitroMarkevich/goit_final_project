package com.example.demo.exception.user;

public class UserAlreadyExistsException extends RuntimeException {
    private static final String USER_ALREADY_EXISTS_EXCEPTION_TEXT = "User with username = %s and email = %s already exists.";

    public UserAlreadyExistsException(String username, String email) {
        super(String.format(USER_ALREADY_EXISTS_EXCEPTION_TEXT, username, email));
    }
}