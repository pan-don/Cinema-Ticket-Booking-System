package com.project.mvc.controller;

import java.time.LocalDate;     // Import class untuk merepresentasikan tanggal
import java.time.LocalTime;    // Import class untuk merepresentasikan waktu (jam tayang)
import java.util.List;        // Import class untuk daftar jadwal

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.model.Jadwal;               // Import model Jadwal
import com.project.mvc.services.JadwalService;    // Import service JadwalService
import com.project.mvc.services.LoginService;    // Import service LoginService

import lombok.RequiredArgsConstructor;

@RestController                      // Menandakan bahwa kelas ini adalah controller REST
@RequestMapping("/api/scheduls")    // Menentukan base URL untuk semua endpoint dalam controller ini
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
    }

    // Endpoint untuk menampilkan semua jadwal tayang film
    @GetMapping("/showAll")
    public ResponseEntity<List<Jadwal>> showAllJadwal() {
        List<Jadwal> jadwals = jadwalService.getAllJadwal();
        return ResponseEntity.ok(jadwals);
    }
}
