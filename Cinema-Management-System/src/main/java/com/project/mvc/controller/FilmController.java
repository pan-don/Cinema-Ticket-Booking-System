package com.project.mvc.controller; // ⬅️ Gunakan huruf kecil untuk konsistensi dan konvensi Java

import com.project.mvc.model.Film;
import com.project.mvc.services.FilmService; // ⬅️ Sesuaikan dengan nama package aslimu (services)
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService; // ⬅️ Pastikan nama class dan variabel konsisten

    // Ambil semua film
    @GetMapping
    public ResponseEntity<List<Film>> getAllFilms() {
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    // Ambil film berdasarkan ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable String id) {
        return filmService.getFilmById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Ambil film berdasarkan judul
    @GetMapping("/judul/{judul}")
    public ResponseEntity<Film> getFilmByJudul(@PathVariable String judul) {
        return filmService.getFilmByJudul(judul)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Simpan atau update film
    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        return ResponseEntity.ok(filmService.saveFilm(film));
    }

    // Hapus film
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        filmService.deleteFilm(id);
        return ResponseEntity.noContent().build();
    }
}
