package com.example.demo.auth;

import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import com.example.demo.user.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserValidator userValidator;
    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        return new ModelAndView("auth/login");
    }

    @PostMapping("/login")
    public ModelAndView loginSubmit() {
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        return new ModelAndView("auth/register");
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error/base-error");
        } else {
            userService.createUser(userDto);
            modelAndView.setViewName("auth/login");
        }
        return modelAndView;
    }
}