package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/settings")
    public ModelAndView getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new ModelAndView("user/settings").addObject("editUser", userService.getUserByUsername(username));
    }

    @PostMapping("/update")
    public void updateAccount(@ModelAttribute UserDto userDto) {
        userService.updateUser(userDto);
    }
}
