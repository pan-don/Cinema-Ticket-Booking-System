package com.project.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mvc.model.Film;
import com.project.mvc.model.Jadwal;

public interface JadwalRepository extends JpaRepository<Jadwal, String> {
    List<Jadwal> findByFilm(Film film);
}