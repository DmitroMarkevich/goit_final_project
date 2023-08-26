package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/account")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/get")
    public ModelAndView getAccount() {
        return new ModelAndView(/*URL*/);
    }

    @PostMapping("/update")
    public void updateAccount(@RequestBody UserEntity userEntity) {
        //to be continued
    }
}
