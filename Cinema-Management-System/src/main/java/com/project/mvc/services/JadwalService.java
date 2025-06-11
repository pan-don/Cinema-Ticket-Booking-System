package com.project.mvc.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mvc.model.Film;
import com.project.mvc.model.Jadwal;
import com.project.mvc.repository.FilmRepository;
import com.project.mvc.repository.JadwalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
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

    @Transactional
    public void deleteJadwal(String jadwalId){
        Jadwal jadwal = jadwalRepo.findById(jadwalId)
        .orElseThrow(() -> new RuntimeException("jadwal not found"));

        jadwalRepo.delete(jadwal);
    }


    public List<Jadwal> getJadwalByFilm(Film film){
        return jadwalRepo.findByFilm(film);
    }

    @Transactional(readOnly=true)
    public List<Jadwal> getAllJadwal(){
        return jadwalRepo.findAll();
    }

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
