package com.project.mvc.services;

import com.project.mvc.model.Jadwal;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class JadwalService {
    private final List<Jadwal> jadwalList = new ArrayList<>();

    // Update jadwal (by id)
    public boolean updateJadwal(String id, Jadwal newJadwal) {
        for (int i = 0; i < jadwalList.size(); i++) {
            if (jadwalList.get(i).getId().equals(id)) {
                jadwalList.set(i, newJadwal);
                return true;
            }
        }
        return false;
    }

    // Delete jadwal (by id)
    public boolean deleteJadwal(String id) {
        Iterator<Jadwal> iterator = jadwalList.iterator();
        while (iterator.hasNext()) {
            Jadwal jadwal = iterator.next();
            if (jadwal.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Get all jadwal
    public List<Jadwal> getAllJadwal() {
        return new ArrayList<>(jadwalList);
    }

    // Cek jadwal bentrok (ruangan pada film sama pada tanggal dan jam yang sama)
    public boolean isJadwalBentrok(Jadwal jadwalBaru) {
        for (Jadwal jadwal : jadwalList) {
            if (jadwal.getFilm().getRuangan().equals(jadwalBaru.getFilm().getRuangan()) &&
                jadwal.getTanggalTayang().equals(jadwalBaru.getTanggalTayang()) &&
                jadwal.getJamTayang().equals(jadwalBaru.getJamTayang())) {
                return true; // Bentrok
            }
        }
        return false;
    }

    // Tambah jadwal (dengan pengecekan bentrok)
    public boolean addJadwal(Jadwal jadwalBaru) {
        if (isJadwalBentrok(jadwalBaru)) {
            return false;
        }
        jadwalList.add(jadwalBaru);
        return true;
    }
}
