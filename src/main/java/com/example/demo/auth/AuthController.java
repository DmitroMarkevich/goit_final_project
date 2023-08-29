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
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginSubmit() {
        ModelAndView modelAndView = new ModelAndView();
        // код для автентифікації користувача
        modelAndView.setViewName("redirect:/note/list");
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registration() {
        return new ModelAndView("auth/register");
    }

    @PostMapping("/register")
    public ModelAndView registration(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            modelAndView.setViewName("error/base-error");
        } else {
            userService.createUser(userDto);
            modelAndView.setViewName("redirect:/note/list");
        }
        return modelAndView;
    }
}

