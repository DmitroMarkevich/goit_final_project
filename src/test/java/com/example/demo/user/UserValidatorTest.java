package com.example.demo.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserValidatorTest {
    @Mock
    private UserValidator userValidator;

    @BeforeEach
    public void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    public void testValidUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("validUsername");
        userDto.setEmail("valid@example.com");
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void testInvalidShortUsername() {
        UserDto userDto = new UserDto();
        userDto.setUsername("abc"); //short name
        userDto.setEmail("valid@example.com");
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testInvalidLongUsername() {
        UserDto userDto = new UserDto();
        userDto.setUsername("veryVeryLongUsernameOfUser1234567890VeryVeryLongUsername"); //long name
        userDto.setEmail("valid@example.com");
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testInvalidEmailFormat() {
        UserDto userDto = new UserDto();
        userDto.setUsername("validUsername");
        userDto.setEmail("invalidEmail"); // invalid email format
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testInvalidEmailLength() {
        UserDto userDto = new UserDto();
        userDto.setUsername("validUsername");
        userDto.setEmail("veryVeryLong1234567890veryVeryLong1234567890long@.com"); //long email
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testInvalidPasswordLength() {
        UserDto userDto = new UserDto();
        userDto.setUsername("validUsername");
        userDto.setEmail("valid@example.com");
        userDto.setPassword("short"); // short password
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testDuplicateEmail() {
        UserDto userDto = new UserDto();
        userDto.setUsername("validUsername");
        userDto.setEmail("existing@example.com");
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void testDuplicateUsername() {
        UserDto userDto = new UserDto();
        userDto.setUsername("existingUsername");
        userDto.setEmail("valid@example.com");
        userDto.setPassword("validPassword");
        userDto.setFirstName("ValidFirstName");
        userDto.setLastName("ValidLastName");

        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");
        userValidator.validate(userDto, errors);

        assertEquals(1, errors.getErrorCount());
    }
}
