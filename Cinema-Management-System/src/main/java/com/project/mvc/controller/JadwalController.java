package com.project.mvc.controller;

import com.project.mvc.model.Jadwal;
import com.project.mvc.services.JadwalService;
import java.util.List;

public class JadwalController {
    private final JadwalService jadwalService;

    public JadwalController(JadwalService jadwalService) {
        this.jadwalService = jadwalService;
    }

    public boolean tambahJadwal(Jadwal jadwal) {
        return jadwalService.addJadwal(jadwal);
    }

    public boolean updateJadwal(String id, Jadwal jadwalBaru) {
        return jadwalService.updateJadwal(id, jadwalBaru);
    }

    public boolean hapusJadwal(String id) {
        return jadwalService.deleteJadwal(id);
    }

    public List<Jadwal> getSemuaJadwal() {
        return jadwalService.getAllJadwal();
    }

    public boolean cekJadwalBentrok(Jadwal jadwal) {
        return jadwalService.isJadwalBentrok(jadwal);
    }
}
