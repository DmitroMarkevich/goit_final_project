package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {
    private final UserValidator userValidator;
    @Autowired
    public AuthController(UserValidator userValidator) {
        this.userValidator = userValidator;
    }
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
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
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("userDto", new UserDto());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registration(@ModelAttribute("userDto") UserDto userDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("Вас зареєстровано!");
        } else {
            //код для збереження користувача в базі даних
            modelAndView.setViewName("redirect:/login");
        }
        return modelAndView;
    }
}

