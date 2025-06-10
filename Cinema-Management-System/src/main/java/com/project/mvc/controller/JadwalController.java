package com.project.mvc.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.model.Jadwal;
import com.project.mvc.services.JadwalService;
import com.project.mvc.services.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/scheduls")
@RequiredArgsConstructor
public class JadwalController {
    private final LoginService loginService;
    private final JadwalService jadwalService;

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

    @GetMapping("/showAll")
    public ResponseEntity<List<Jadwal>> showAllJadwal() {
        List<Jadwal> jadwals = jadwalService.getAllJadwal();
        return ResponseEntity.ok(jadwals);
    }
}
