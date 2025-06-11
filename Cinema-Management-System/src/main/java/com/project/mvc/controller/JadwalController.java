package com.project.mvc.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.dto.FilmDTO;
import com.project.mvc.dto.JadwalDTO;
import com.project.mvc.model.Film;
import com.project.mvc.model.Jadwal;
import com.project.mvc.services.JadwalService;
import com.project.mvc.services.LoginService;               // Import model Jadwal

import lombok.RequiredArgsConstructor;    // Import service JadwalService

@RestController                      // Menandakan bahwa kelas ini adalah controller REST
@RequestMapping("/api/schedules")    // Menentukan base URL untuk semua endpoint dalam controller ini
@RequiredArgsConstructor           // Menggunakan Lombok untuk meng-generate constructor dengan parameter final

// Controller untuk menangani jadwal tayang film
public class JadwalController {
    private final LoginService loginService;
    private final JadwalService jadwalService;

    // Endpoint untuk membuat jadwal tayang film baru
    @PostMapping("/create")
    public ResponseEntity<Jadwal> createJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String filmId,
        @RequestParam LocalTime jamTayang,
        @RequestParam LocalDate tanggalTayang
    ){
        loginService.loginAdmin(username, password);
        Jadwal createdJadwal = jadwalService.createJadwal(filmId, jamTayang, tanggalTayang); 
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJadwal);
    }

    // Endpoint untuk memperbarui jadwal tayang film yang sudah ada
    @PostMapping("/update")
    public ResponseEntity<Jadwal> updateJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId,
        @RequestParam LocalTime jamTayang,
        @RequestParam LocalDate tanggalTayang
    ) {
        loginService.loginAdmin(username, password);
        Jadwal updatedJadwal =  jadwalService.updateJadwal(jadwalId, jamTayang, tanggalTayang);
        return ResponseEntity.ok(updatedJadwal);
    }
 
    // Endpoint untuk menghapus jadwal tayang film
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId
    ) {
        loginService.loginAdmin(username, password);
        jadwalService.deleteJadwal(jadwalId);
        return ResponseEntity.noContent().build();
    }    // Endpoint untuk menampilkan semua jadwal tayang film
    @GetMapping("/showAll")
    public ResponseEntity<List<JadwalDTO>> showAllJadwal() {
        List<Jadwal> jadwals = jadwalService.getAllJadwalEntities();
        
        List<JadwalDTO> dtos = jadwals.stream()
            .filter(jadwal -> jadwal != null && jadwal.getFilm() != null)
            .map(jadwal -> {
                JadwalDTO jadwalDTO = new JadwalDTO();
                jadwalDTO.setId(jadwal.getId());
                jadwalDTO.setJamTayang(jadwal.getJamTayang());
                jadwalDTO.setTanggalTayang(jadwal.getTanggalTayang());
                
                Film film = jadwal.getFilm();
                FilmDTO filmDTO = new FilmDTO();
                filmDTO.setId(film.getId());
                filmDTO.setJudul(film.getJudul());
                filmDTO.setGenre(film.getGenre());
                filmDTO.setSinopsis(film.getSinopsis());
                filmDTO.setDurasi(film.getDurasi());
                filmDTO.setRuangan(film.getRuangan());
                filmDTO.setKapasitasRuangan(film.getKapasitasRuangan());
                filmDTO.setHarga(film.getHarga());
                
                jadwalDTO.setFilm(filmDTO);
                return jadwalDTO;
            })
            .collect(Collectors.toList());
        
        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(dtos);
    }
}
