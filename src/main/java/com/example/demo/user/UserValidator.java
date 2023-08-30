package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        String username = userDto.getUsername();
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        validateUsername(username, errors);
        validateEmail(email, errors);
        validatePassword(password, errors);
        validateUniqueEmailAndUsername(email, username, errors);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty", "Enter your e-mail!");
    }

    private void validateUsername(String username, Errors errors) {
        if (username.length() < 5 || username.length() > 50) {
            errors.rejectValue("username", "user.username.invalid", "Error - enter login from 5 to 50 characters!");
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
        if (password.length() < 8 || password.length() > 100) {
            errors.rejectValue("password", "user.password.invalid", "Invalid password. Enter at least 8 characters!");
        }
    }

    private void validateUniqueEmailAndUsername(String email, String username, Errors errors) {
        if (userRepository.existsByEmail(email)) {
            errors.rejectValue("email", "user.email.invalid", "User with email = %s already exists.");
        }
        if (userRepository.existsByUsername(username)) {
            errors.rejectValue("username", "user.username.invalid", "User with username = %s already exists.");
        }
    }
}
