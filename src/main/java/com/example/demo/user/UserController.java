package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserValidator userValidator;
    private final UserService userService;

    @GetMapping("/settings")
    public ModelAndView getAccount() {
        return new ModelAndView("user/settings").addObject("editUser", userService.getUser());
    }

    @PostMapping("/update")
    public ModelAndView updateAccount(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();

        userValidator.validate(userDto, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("error/base-error");
        } else {
            userService.updateUser(userDto);
            modelAndView.setViewName("redirect:/user/settings");
        }

        return modelAndView;
    }
}