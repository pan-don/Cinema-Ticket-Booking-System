package com.project.mvc.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mvc.model.Film;
import com.project.mvc.repository.FilmRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmRepository filmRepo;

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

    @Transactional
    public Film updateFilm(
        String filmId,
        String ruangan,
        int kapasitasRuangan,
        int harga
    ) {
        Film film = filmRepo.findById(filmId)
        .orElseThrow(() -> new RuntimeException("Film not found"));

        film.setRuangan(ruangan);
        film.setKapasitasRuangan(kapasitasRuangan);
        film.setHarga(harga);

        return filmRepo.save(film);
    }

    public void deleteFilm(String filmId){
        Film film = filmRepo.findById(filmId)
        .orElseThrow(() -> new RuntimeException("Film not found"));

        filmRepo.delete(film);
    }

    

    @Transactional(readOnly=true)
    public List<Film> getAllFilm(){
        return filmRepo.findAll();
    }
}