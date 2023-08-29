package com.example.demo.user;

import lombok.RequiredArgsConstructor;
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
        return new ModelAndView("user/settings");
    }

    @PostMapping("/update")
    public void updateAccount(@RequestBody UserEntity userEntity) {
        //to be continued
    }
}
