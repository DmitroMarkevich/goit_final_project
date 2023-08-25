package com.example.demo.auth;

import com.example.demo.user.UserEntity;
import com.example.demo.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerUser(@RequestBody UserEntity userEntity) {

    }

    @GetMapping("/login")
    private String getLoginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "auth/register";
    }
}
