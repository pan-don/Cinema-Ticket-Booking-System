package com.project.mvc.controller;

import java.time.LocalTime;
import java.util.List;

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
    public Film createFilm(
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
        return filmService.createFilm(judul, genre, sinopsis, durasi, ruangan, kapasitasRuangan, harga, tiketTerjual);
    }

    @PostMapping("/update")
    public Film updateFilm(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String filmId,
        @RequestParam String ruangan,
        @RequestParam int kapasitasRuangan,
        @RequestParam int harga
    ) {
        loginService.loginAdmin(username, password);
        return filmService.updateFilm(filmId, ruangan, kapasitasRuangan, harga);
    }

    @PostMapping("/delete")
    public void deleteFilm(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String filmId
    ) {
        loginService.loginAdmin(username, password);
        filmService.deleteFilm(filmId);
    }

    @PostMapping("/showAll")
    public List<Film> showAllFilm(
        @RequestParam String username,
        @RequestParam String password
    ){
        loginService.loginUser(username, password);
        return filmService.getAllFilm();
    }
}
