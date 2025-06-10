package com.project.mvc.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.model.Film;
import com.project.mvc.services.FilmService;
import com.project.mvc.services.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {
    private final LoginService loginService;
    private final FilmService filmService;

    @PostMapping("/create")
    public ResponseEntity<Film> createFilm(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String judul,
        @RequestParam String genre,
        @RequestParam String sinopsis,
        @RequestParam LocalTime durasi,
        @RequestParam String ruangan,
        @RequestParam int kapasitasRuangan,
        @RequestParam int harga,
        @RequestParam int tiketTerjual
    ) {
        loginService.loginAdmin(username, password);
        
        Film createdFilm = filmService.createFilm(judul, genre, sinopsis, durasi, ruangan, 
            kapasitasRuangan, harga, tiketTerjual);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    @PostMapping("/update")
    public ResponseEntity<Film> updateFilm(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String filmId,
        @RequestParam String ruangan,
        @RequestParam int kapasitasRuangan,
        @RequestParam int harga
    ) {
        loginService.loginAdmin(username, password);
        Film updatedFilm = filmService.updateFilm(filmId, ruangan, kapasitasRuangan, harga);
        return ResponseEntity.ok(updatedFilm);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> deleteFilm(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String filmId
    ) {
        loginService.loginAdmin(username, password);
        filmService.deleteFilm(filmId);
        return ResponseEntity.noContent().build();
    }    
    
    @PostMapping("/showAlluser")
    public ResponseEntity<List<Film>> showAllFilmUser(
        @RequestParam String username,
        @RequestParam String password
    ){
        loginService.loginUser(username, password);
        List<Film> films = filmService.getAllFilm();
        return ResponseEntity.ok(films);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilm();
        return ResponseEntity.ok(films);
    }
}
