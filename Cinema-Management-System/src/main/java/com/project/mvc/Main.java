package com.project.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.project.mvc.model.Admin;
import com.project.mvc.model.User;
import com.project.mvc.repository.AdminRepository;
import com.project.mvc.repository.UserRepository;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner initialDatabase(UserRepository userRepo, AdminRepository adminRepo){
		return args -> {
			if (userRepo.count() == 0) {
				User user = new User();
				user.setUsername("customer");
				user.setPassword("123");
				user.setRole("USER");
				userRepo.save(user);
			}

			if (adminRepo.count() == 0) {
				Admin admin = new Admin();
				admin.setUsername("admin");
				admin.setPassword("admin123");
				admin.setRole("ADMIN");
				adminRepo.save(admin);
			}
		};
	}
}
