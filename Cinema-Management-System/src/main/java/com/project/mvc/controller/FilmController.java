package com.project.mvc.controller;

import java.time.LocalTime;              // Import untuk LocalTime yang digunakan untuk durasi film
import java.util.List;              // Import untuk List yang digunakan untuk daftar film

import org.springframework.http.HttpStatus;                 // Import untuk status HTTP
import org.springframework.http.ResponseEntity;             // Import untuk ResponseEntity
import org.springframework.web.bind.annotation.GetMapping;  // Import untuk anotasi GetMapping
import org.springframework.web.bind.annotation.PostMapping; // Import untuk anotasi PostMapping
import org.springframework.web.bind.annotation.RequestMapping;  // Import untuk anotasi RequestMapping
import org.springframework.web.bind.annotation.RequestParam;    // Import untuk anotasi RequestParam
import org.springframework.web.bind.annotation.RestController;  // Import untuk anotasi RestController

import com.project.mvc.model.Film;                             // Import model Film
import com.project.mvc.services.FilmService;                   // Import service FilmService
import com.project.mvc.services.LoginService;                 // Import service LoginService

import lombok.RequiredArgsConstructor;        // Import untuk anotasi Lombok yang meng-generate constructor dengan semua field sebagai parameter

@RestController     // Menandakan bahwa kelas ini adalah controller REST
@RequestMapping("/api/films")     // Menentukan base URL untuk semua endpoint dalam controller ini
@RequiredArgsConstructor          // Menggunakan Lombok untuk meng-generate constructor dengan parameter final field
public class FilmController {
    private final LoginService loginService;
    private final FilmService filmService;

    // Endpoint untuk membuat film baru
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
        loginService.loginAdmin(username, password);             // Memanggil service login untuk memverifikasi admin
        
        Film createdFilm = filmService.createFilm(judul, genre, sinopsis, durasi, ruangan, 
            kapasitasRuangan, harga, tiketTerjual);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }   

    // Endpoint untuk memperbarui film yang sudah ada
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

    // Endpoint untuk menghapus film
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
    
    // Endpoint untuk menampilkan semua film untuk user
    @PostMapping("/showAlluser")
    public ResponseEntity<List<Film>> showAllFilmUser(
        @RequestParam String username,
        @RequestParam String password
    ){
        loginService.loginUser(username, password);
        List<Film> films = filmService.getAllFilm();
        return ResponseEntity.ok(films);
    }

    // Endpoint untuk menampilkan semua film untuk admin
    @GetMapping("/all")
    public ResponseEntity<List<Film>> getAllFilms() {
        List<Film> films = filmService.getAllFilm();
        return ResponseEntity.ok(films);
    }
}
