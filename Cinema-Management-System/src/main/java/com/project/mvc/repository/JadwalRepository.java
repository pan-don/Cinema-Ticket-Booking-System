package com.project.mvc.repository;

import com.project.mvc.model.Jadwal;
import com.project.mvc.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.time.LocalDate;

public interface JadwalRepository extends JpaRepository<Jadwal, String> {
    Optional<Jadwal> findByFilm(Film film);
    Optional<Jadwal> findByTanggal(LocalDate tanggal);
    
}
