package com.example.demo.user;

import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class UserValidatorTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserDto() {
        UserDto userDto = UserDto.builder()
                .username("validUsername")
                .email("alid3723@gmail.com")
                .password("validPassword")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidShortUsername() {
        UserDto userDto = UserDto.builder()
                .username("abc") // Short username
                .email("alid3723@example.com")
                .password("validPassword")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidLongUsername() {
        UserDto userDto = UserDto.builder()
                .username("veryVeryLongUsernameOfUser1234567890VeryVeryLongUsername") // Long username
                .email("alid3723@example.com")
                .password("validPassword")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidEmailFormat() {
        UserDto userDto = UserDto.builder()
                .username("validUsername")
                .email("invalidEmail")
                .password("validPassword")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidEmailLength() {
        UserDto userDto = UserDto.builder()
                .username("validUsername")
                .email("veryVeryLong1234567890veryVeryLong1234567890long@gmail.com")
                .password("validPassword")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @Test
    public void testInvalidPasswordLength() {
        UserDto userDto = UserDto.builder()
                .username("validUsername")
                .email("veryVeryLong1234567890veryVeryLong1234567890long@gmail.com")
                .password("short")
                .firstName("ValidFirstName")
                .lastName("ValidLastName")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
        Assertions.assertFalse(violations.isEmpty());
    }
}
