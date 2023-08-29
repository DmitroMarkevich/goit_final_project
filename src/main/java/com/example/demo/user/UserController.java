package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return new ModelAndView("user/settings").addObject("editUser", userService.getUserByUsername(username));
    }

    @PostMapping("/update")
    public void updateAccount(@ModelAttribute UserDto userDto) {
        userService.updateUser(userDto);
    }
}
