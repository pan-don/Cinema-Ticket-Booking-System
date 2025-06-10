package com.project.mvc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.model.Admin;
import com.project.mvc.model.User;
import com.project.mvc.services.LoginService;

import lombok.RequiredArgsConstructor;


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