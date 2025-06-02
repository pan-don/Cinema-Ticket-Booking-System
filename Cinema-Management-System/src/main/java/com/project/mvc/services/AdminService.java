package com.project.mvc.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.mvc.model.*;
import com.project.mvc.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FilmRepository filmRepo;
    // private final JadwalRepository jadwalRepo;

    @Transactional
    public Film createFilm(
        String judul,
        String genre,
        String sinopsis,
        LocalTime durasi,
        String ruangan,
        int kapasitasRuangan,
        int harga,
        int tiketTerjual
        ) {
        if(filmRepo.findByJudul(judul).isPresent()) {
            throw new RuntimeException("Judul already exists");
        }

        Film film = new Film();
        film.setJudul(judul);
        film.setGenre(genre);
        film.setSinopsis(sinopsis);
        film.setDurasi(durasi);
        film.setRuangan(ruangan);
        film.setKapasitasRuangan(kapasitasRuangan);
        film.setHarga(harga);
        film.setTiketTerjual(tiketTerjual);

        return filmRepo.save(film);
    }

    public Film updateFilm(
        String filmId,
        LocalTime durasi,
        String ruangan,
        int kapasitasRuangan,
        int harga
    ) {
        Film film = filmRepo.findById(filmId)
        .orElseThrow(() -> new RuntimeException("Film not found"));

        film.setDurasi(durasi);
        film.setRuangan(ruangan);
        film.setKapasitasRuangan(kapasitasRuangan);
        film.setHarga(harga);

        return filmRepo.save(film);
    }

    public List<Film> getAllFilm(){
        return filmRepo.findAll();
    }
}

