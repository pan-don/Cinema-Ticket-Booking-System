package com.project.mvc.services;

import com.project.mvc.model.Film;
import com.project.mvc.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepo;

    // Simpan atau update film
    public Film saveFilm(Film film) {
        return filmRepo.save(film);
    }

    // Ambil semua film
    public List<Film> getAllFilms() {
        return filmRepo.findAll();
    }

    // Cari film berdasarkan judul
    public Optional<Film> getFilmByJudul(String judul) {
        return filmRepo.findByJudul(judul);
    }

    // Cari film berdasarkan ID
    public Optional<Film> getFilmById(String id) {
        return filmRepo.findById(id);
    }

    // Hapus film berdasarkan ID
    public void deleteFilm(String id) {
        filmRepo.deleteById(id);
    }
}
