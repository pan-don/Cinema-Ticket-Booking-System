package com.project.mvc.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public Jadwal createJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestBody String filmId,
        @RequestParam LocalTime jamTayang,
        @RequestParam LocalDate tanggalTayang
    ){
        loginService.loginAdmin(username, password);
        return jadwalService.createJadwal(filmId, jamTayang, tanggalTayang);
    }

    @PostMapping("/update")
    public Jadwal updateJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId,
        @RequestParam LocalTime jamTayang,
        @RequestParam LocalDate tanggalTayang
    ) {
        loginService.loginAdmin(username, password);
        return jadwalService.updateJadwal(jadwalId, jamTayang, tanggalTayang);
    }

    @PostMapping("/delete")
    public void deleteJadwal(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId
    ) {
        loginService.loginAdmin(username, password);
        jadwalService.deleteJadwal(jadwalId);
    }

    @PostMapping("/showAll")
    public List<Jadwal> showAllJadwal(
        @RequestParam String username,
        @RequestParam String password
    ) {
        loginService.loginUser(username, password);
        return jadwalService.getAllJadwal();
    }
}
