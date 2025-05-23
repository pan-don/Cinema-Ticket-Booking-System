package com.project.mvc.repository;

import com.project.mvc.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FilmRepository extends JpaRepository<Film, String> {
    Optional<Film> findByTitle(String judul);
    Optional<Film> findById(String id);
}
