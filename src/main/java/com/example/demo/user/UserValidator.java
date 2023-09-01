package com.example.demo.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private static final int MIN_USERNAME_LENGTH = 5;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 100;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof UserDto userDto) {
            validateUsername(userDto.getUsername(), errors);
            validateEmail(userDto.getEmail(), errors);
            validatePassword(userDto.getPassword(), errors);

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty", "Enter your e-mail!");
        }
    }

    private void validateUsername(String username, Errors errors) {
        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH) {
            errors.rejectValue("username", "user.username.invalid", "Error - enter login from " + MIN_USERNAME_LENGTH + " to " + MAX_USERNAME_LENGTH + " characters!");
        } else if (!username.matches("^[a-zA-Z0-9]+$")) {
            errors.rejectValue("username", "user.username.invalidchars", "The login can contain only Latin letters and numbers!");
        }
    }

    private void validateEmail(String email, Errors errors) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            errors.rejectValue("email", "user.email.invalidchars", "This email address is not valid.");
        }
    }

    private void validatePassword(String password, Errors errors) {
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            errors.rejectValue("password", "user.password.invalid", "Invalid password. Enter at least " + MIN_PASSWORD_LENGTH + " characters!");
        }
    }
}