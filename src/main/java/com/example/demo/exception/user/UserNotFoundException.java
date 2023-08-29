package com.example.demo.exception.user;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND_EXCEPTION_TEXT = "User with %s = %s is not found.";

    public UserNotFoundException(UUID userId) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_TEXT, "id", userId));
    }

    public UserNotFoundException(String key, String value) {
        super(String.format(USER_NOT_FOUND_EXCEPTION_TEXT, key, value));
    }
}
