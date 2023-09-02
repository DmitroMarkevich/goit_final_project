package com.example.demo.auth;

import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class AuthControllerTest {
    @Test
    public void testShowLoginForm() {
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userService);

        Authentication authentication = mock(Authentication.class);

        ModelAndView modelAndView = authController.showLoginForm(authentication);
        assertEquals("auth/login", modelAndView.getViewName());
    }

    @Test
    public void testShowRegistrationForm() {
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userService);

        Authentication authentication = mock(Authentication.class);

        ModelAndView modelAndView = authController.showRegistrationForm(authentication);
        assertEquals("auth/register", modelAndView.getViewName());
    }

    @Test
    public void testRegisterUser_ValidUser() {
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userService);

        UserDto userDto = new UserDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView modelAndView = authController.registerUser(userDto, bindingResult);

        verify(userService).createUser(userDto);
        assertEquals("auth/login", modelAndView.getViewName());
    }

    @Test
    public void testRegisterUser_InvalidUser() {
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userService);

        UserDto userDto = new UserDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView modelAndView = authController.registerUser(userDto, bindingResult);

        verify(userService, never()).createUser(userDto);
        assertEquals("error/base-error", modelAndView.getViewName());
    }
}


