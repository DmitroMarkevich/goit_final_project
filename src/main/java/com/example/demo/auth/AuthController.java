package com.example.demo.auth;

import com.example.demo.exception.user.EmailAlreadyUsedException;
import com.example.demo.exception.user.UsernameAlreadyUsedException;
import com.example.demo.user.UserDto;
import com.example.demo.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/")
    public ModelAndView redirectToNoteList() {
        return new ModelAndView("redirect:/note/list");
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm(Authentication authentication) {
        if (userService.isAuthenticated(authentication)) {
            return new ModelAndView("redirect:/note/list");
        }

        return new ModelAndView("auth/login");
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm(Authentication authentication) {
        if (userService.isAuthenticated(authentication)) {
            return new ModelAndView("redirect:/note/list");
        }

        return new ModelAndView("auth/register");
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) throws EmailAlreadyUsedException, UsernameAlreadyUsedException {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error/base-error");
        } else {
            userService.createUser(userDto);
            modelAndView.setViewName("auth/login");
        }
        return modelAndView;
    }
}