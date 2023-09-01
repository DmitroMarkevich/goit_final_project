package com.example.demo.auth;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
public class AuthControllerTest {
    @Test
    public void testShowLoginForm() {
        AuthController authController = new AuthController(null, null);
        ModelAndView modelAndView = authController.showLoginForm();
        assertEquals("auth/login", modelAndView.getViewName());
    }

    @Test
    public void testLoginSubmit() {
        AuthController authController = new AuthController(null, null);
        ModelAndView modelAndView = authController.loginSubmit();
        assertEquals("redirect:/note/list", modelAndView.getViewName());
    }

    @Test
    public void testShowRegistrationForm() {
        AuthController authController = new AuthController(null, null);
        ModelAndView modelAndView = authController.showRegistrationForm();
        assertEquals("auth/register", modelAndView.getViewName());
    }

    @Test
    public void testRegisterUser_ValidUser() {
        UserValidator userValidator = mock(UserValidator.class);
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userValidator, userService);

        UserDto userDto = new UserDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView modelAndView = authController.registerUser(userDto, bindingResult);

        verify(userValidator).validate(userDto, bindingResult);
        verify(userService).createUser(userDto);
        assertEquals("auth/login", modelAndView.getViewName());
    }

    @Test
    public void testRegisterUser_InvalidUser() {
        UserValidator userValidator = mock(UserValidator.class);
        UserService userService = mock(UserService.class);
        AuthController authController = new AuthController(userValidator, userService);

        UserDto userDto = new UserDto();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView modelAndView = authController.registerUser(userDto, bindingResult);

        verify(userValidator).validate(userDto, bindingResult);
        verify(userService, never()).createUser(userDto);
        assertEquals("error/base-error", modelAndView.getViewName());
    }
}


