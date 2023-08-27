package com.example.demo.user;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto) target;

        if (userDto.getUsername().length() < 5 || userDto.getUsername().length() > 50) {
            errors.rejectValue("username", "user.username.invalid", "Error - enter login from 5 to 50 characters!");
        } else if (!userDto.getUsername().matches("^[a-zA-Z0-9]+$")) {
            errors.rejectValue("username", "user.username.invalidchars", "The login can contain only Latin letters and numbers!");
        }
        if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 100) {
            errors.rejectValue("password", "user.password.invalid", "Invalid password. Enter at least 8 characters!");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty", "Enter your e-mail!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "user.firstName.empty", "Enter your first name!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "user.lastName.empty", "Enter your last name!");
    }
}

