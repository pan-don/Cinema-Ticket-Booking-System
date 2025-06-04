package com.project.mvc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mvc.model.Film;

public interface FilmRepository extends JpaRepository<Film, String> {
    Optional<Film> findByJudul(String judul);
}