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
        errors.rejectValue("username", "user.username.invalid", "Помилка - введіть логін від 5 до 50 символів!");
    } else if (!userDto.getUsername().matches("^[a-zA-Z0-9]+$")) {
        errors.rejectValue("username", "user.username.invalidchars", "Логін може містити лише латинські букви та цифри!");
    }
    if (userDto.getPassword().length() < 8 || userDto.getPassword().length() > 100) {
        errors.rejectValue("password", "user.password.invalid", "Невірний пароль. Введіть не менше 8 символів!");
    }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "user.email.empty", "Заповніть поле електронної пошти!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "user.firstName.empty", "Введіть Ваше ім'я");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "user.lastName.empty", "Введіть Ваше прізвище!");

}}

