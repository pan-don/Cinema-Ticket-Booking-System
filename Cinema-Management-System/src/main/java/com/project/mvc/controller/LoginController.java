package com.project.mvc.controller;

import com.project.mvc.model.*;
import com.project.mvc.services.*;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/Authenticator")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/register")
    public User userRegister(@RequestParam String username, @RequestParam String password) {
        return loginService.registerUser(username, password);
    }

    @PostMapping("/login/user")
    public User loginUser(@RequestParam String username, @RequestParam String password){
        return loginService.loginUser(username, password);
    }

    @PostMapping("/login/admin")
    public Admin loginAdmin(@RequestParam String username, @RequestParam String password){
        return loginService.loginAdmin(username, password);
    }
}
