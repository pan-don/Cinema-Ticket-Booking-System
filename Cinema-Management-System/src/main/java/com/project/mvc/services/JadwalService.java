package com.project.mvc.services;

import java.time.LocalDate;           // Import class untuk merepresentasikan tanggal
import java.time.LocalTime;           // Import class untuk merepresentasikan waktu (jam tayang)
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;                // Import class untuk daftar jadwal
import java.util.Map;

import org.springframework.stereotype.Service;                          // Import untuk anotasi service
import org.springframework.transaction.annotation.Transactional;       // Import untuk anotasi transaksi

import com.project.mvc.model.Film;                         // Import model Film
import com.project.mvc.model.Jadwal;                       // Import model Jadwal
import com.project.mvc.repository.FilmRepository;          // Import repository FilmRepository
import com.project.mvc.repository.JadwalRepository;       // Import repository JadwalRepository

import lombok.RequiredArgsConstructor;

@Service                       // Menandakan bahwa kelas ini adalah service Spring
@RequiredArgsConstructor       // Menggunakan Lombok untuk meng-generate constructor dengan parameter final field

// Service untuk menangani logika bisnis terkait jadwal tayang film
public class JadwalService {
    private final FilmRepository filmRepo;
    private final JadwalRepository jadwalRepo;
    
    // Menyimpan data jadwal baru ke database 
    @Transactional
    public Jadwal createJadwal(
        String filmId, 
        LocalTime jamTayang, 
        LocalDate tanggalTayang
    ) {
        //mencari film berdasarkan id
        Film film = filmRepo.findById(filmId)
        .orElseThrow(() -> new RuntimeException("Film not found"));
        //Validasi agar jadwal tidak bentrok
       if(jadwalAvailable(film, jamTayang, tanggalTayang)){
        throw new RuntimeException("Jadwal already exist");
       }
        // membuat objek jadwal baru
        Jadwal jadwal = new Jadwal();

        jadwal.setFilm(film);
        jadwal.setJamTayang(jamTayang);
        jadwal.setTanggalTayang(tanggalTayang);
       
        return jadwalRepo.save(jadwal);
    }

    //memperbarui data jadwal yang sudah ada
    @Transactional
    public Jadwal updateJadwal(
        String jadwalId, 
        LocalTime jamTayang, 
        LocalDate tanggalTayang
    ) {
        Jadwal jadwal = jadwalRepo.findById(jadwalId)
        .orElseThrow(() -> new RuntimeException("Jadwal not found"));

        if(jadwalAvailable(jadwal.getFilm(), jamTayang, tanggalTayang)){
        throw new RuntimeException("Jadwal already exist");
       }

        jadwal.setJamTayang(jamTayang);
        jadwal.setTanggalTayang(tanggalTayang);

        return jadwalRepo.save(jadwal);
    }

    //menghapus jadwal berdasarkan id
    @Transactional
    public void deleteJadwal(String jadwalId){
        Jadwal jadwal = jadwalRepo.findById(jadwalId)
        .orElseThrow(() -> new RuntimeException("jadwal not found"));

        jadwalRepo.delete(jadwal);
    }

    // Mengambil jadwal berdasarkan ID
    public List<Jadwal> getJadwalByFilm(Film film){
        return jadwalRepo.findByFilm(film);
    }

    // Mengambil semua jadwal dari database
    @Transactional(readOnly=true)
    public List<Jadwal> getAllJadwal(){
        return jadwalRepo.findAll();
    }

    // Mengambil semua jadwal dari database beserta judul film
    @Transactional(readOnly=true)
    public List<Map<String, Object>> getAllJadwalWithFilmTitle() {
        List<Jadwal> jadwals = jadwalRepo.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Jadwal jadwal : jadwals) {
            Map<String, Object> scheduleData = new HashMap<>();
            scheduleData.put("id", jadwal.getId());
            scheduleData.put("jamTayang", jadwal.getJamTayang());
            scheduleData.put("tanggalTayang", jadwal.getTanggalTayang());
            scheduleData.put("filmTitle", jadwal.getFilm().getJudul());
            result.add(scheduleData);
        }

        return result;
    }

    // Mengambil semua jadwal dari database beserta judul film dan detail ruangan
    @Transactional(readOnly=true)
    public List<Map<String, Object>> getAllJadwalWithFilmDetails() {
        List<Map<String, Object>> jadwalWithDetails = new ArrayList<>();
        for (Jadwal jadwal : jadwalRepo.findAll()) {
            Map<String, Object> jadwalMap = new HashMap<>();
            jadwalMap.put("id", jadwal.getId());
            jadwalMap.put("jamTayang", jadwal.getJamTayang());
            jadwalMap.put("tanggalTayang", jadwal.getTanggalTayang());
            jadwalMap.put("filmTitle", jadwal.getFilm().getJudul());
            jadwalMap.put("filmRoom", jadwal.getFilm().getRuangan());
            jadwalWithDetails.add(jadwalMap);
        }
        return jadwalWithDetails;
    }

    // Memeriksa apakah jadwal tayang film tersedia atau tidak
    public boolean jadwalAvailable(Film film, LocalTime jamTayang, LocalDate tanggalTayang){
        if (tanggalTayang.isBefore(LocalDate.now())) {
            return true;
        }
        //ambil semua jadwal
        List<Jadwal> listJadwal = getAllJadwal();

        for(Jadwal jadwal: listJadwal){
            if(jadwal.getJamTayang().equals(jamTayang) 
            && jadwal.getTanggalTayang().equals(tanggalTayang)
            && jadwal.getFilm().getRuangan().equalsIgnoreCase(film.getRuangan())
            ) {return true;} //bentrok
        }
        return false; //tidak bentrok
    }
}
