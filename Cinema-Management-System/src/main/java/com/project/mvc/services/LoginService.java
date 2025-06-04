package com.project.mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.mvc.model.*;
import com.project.mvc.repository.*;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepo;
    private final AdminRepository adminRepo;

    public User registerUser(String username, String password) {
        if (userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); 
        user.setRole("USER");

        return userRepo.save(user);
    }

    public User loginUser(String username, String password) {
        return userRepo.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public Admin loginAdmin(String username, String password) {
        return adminRepo.findByUsername(username)
                .filter(admin -> admin.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }
}