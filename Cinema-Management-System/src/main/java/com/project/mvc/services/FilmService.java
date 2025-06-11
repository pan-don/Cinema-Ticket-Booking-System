package com.project.mvc.services;

import java.time.LocalTime;       // Import class untuk merepresentasikan waktu (durasi)
import java.util.List;            // Import class untuk daftar film

import org.springframework.stereotype.Service;                       // Import untuk anotasi service
import org.springframework.transaction.annotation.Transactional;     // Import untuk anotasi transaksi

import com.project.mvc.model.Film;                      // Import model Film
import com.project.mvc.repository.FilmRepository;      // Import repository Film

import lombok.RequiredArgsConstructor;    // Import untuk anotasi Lombok yang meng-generate constructor dengan semua field sebagai parameter

@Service                     // Menandakan bahwa kelas ini adalah service Spring
@RequiredArgsConstructor     // Menggunakan Lombok untuk meng-generate constructor dengan parameter final field
public class FilmService {
    private final FilmRepository filmRepo;    // Repository untuk akses data film 

    private void validatePositiveNumber(String fieldName, int value) {
        if (value < 0) {
            throw new RuntimeException(fieldName + " cannot be negative");
        }
    }  // Method untuk validasi angka positif, memberikan exception jika nilai negatif

    @Transactional                   // Menandakan bahwa metode ini akan dijalankan dalam transaksi
    public Film createFilm(         // Method untuk membuat film baru
        String judul,
        String genre,
        String sinopsis,
        LocalTime durasi,
        String ruangan,
        int kapasitasRuangan,
        int harga,
        int tiketTerjual
    ) {
        if(filmRepo.findByJudul(judul).isPresent()) {
            throw new RuntimeException("Judul already exists");
        } // filmRepo.findByJudul(judul) untuk mengecek apakah judul film sudah ada

        // Validate positive numbers
        validatePositiveNumber("Room capacity", kapasitasRuangan);
        validatePositiveNumber("Price", harga);
        validatePositiveNumber("Sold tickets", tiketTerjual);

        // Membuat detail baru dari Film
        Film film = new Film();      
        film.setJudul(judul);
        film.setGenre(genre);
        film.setSinopsis(sinopsis);
        film.setDurasi(durasi);
        film.setRuangan(ruangan);
        film.setKapasitasRuangan(kapasitasRuangan);
        film.setHarga(harga);
        film.setTiketTerjual(tiketTerjual);

        return filmRepo.save(film);
    }   // Method untuk menyimpan film baru ke database

    // Metode ini akan dijalankan dalam transaksi
    @Transactional   
    public Film updateFilm(
        String filmId,
        String ruangan,
        int kapasitasRuangan,
        int harga
    ) {
        // Validate positive numbers
        validatePositiveNumber("Room capacity", kapasitasRuangan);
        validatePositiveNumber("Price", harga);

        Film film = filmRepo.findById(filmId)
        .orElseThrow(() -> new RuntimeException("Film not found"));

        film.setRuangan(ruangan);                        // Mengupdate ruangan film
        film.setKapasitasRuangan(kapasitasRuangan);     // Mengupdate kapasitas ruangan
        film.setHarga(harga);                          // Mengupdate harga tiket

        return filmRepo.save(film);                  // Menyimpan perubahan film ke database
    }

    public void deleteFilm(String filmId){         // Method untuk menghapus film berdasarkan ID
        Film film = filmRepo.findById(filmId)     // Mencari film berdasarkan ID
        .orElseThrow(() -> new RuntimeException("Film not found"));

        filmRepo.delete(film);
    }  
    // Menghapus film dari database

    
    // Menandakan bahwa metode ini hanya membaca data, tidak mengubahnya
    @Transactional(readOnly=true)   
    public List<Film> getAllFilm(){
        return filmRepo.findAll();
    }
}   
// Method untuk mendapatkan semua film dari database