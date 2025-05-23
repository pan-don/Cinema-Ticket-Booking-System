package com.project.mvc.repository;

import com.project.mvc.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, String> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findById(String id);
}
