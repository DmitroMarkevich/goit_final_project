package com.example.demo.exception.user;

public class UsernameAlreadyUsedException extends Exception {
    private static final String USERNAME_ALREADY_EXIST_EXCEPTION_TEXT = "User with username %s already exist.";

    public UsernameAlreadyUsedException(String username) {
        super(String.format(USERNAME_ALREADY_EXIST_EXCEPTION_TEXT, username));
    }
}
