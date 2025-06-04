package com.project.mvc.services;

import com.project.mvc.model.Film;
import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;
import com.project.mvc.repository.FilmRepository;
import com.project.mvc.repository.TicketRepository;
import com.project.mvc.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransaksiService {
    private final FilmRepository filmRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public TransaksiService(FilmRepository filmRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.filmRepository = filmRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    // Logika bisnis pembelian tiket film
    public String beliTiket(String namaFilm, int jumlahTiket, String username) {
        // Cari film berdasarkan nama
        Optional<Film> filmOpt = filmRepository.findByNama(namaFilm);
        if (filmOpt.isEmpty()) {
            return "Film tidak ditemukan!";
        }
        Film film = filmOpt.get();

        // Cek ketersediaan kursi
        if (film.getKuota() < jumlahTiket) {
            return "Kuota tidak mencukupi! Sisa kuota: " + film.getKuota();
        }

        // Cari user berdasarkan username
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "User tidak ditemukan!";
        }
        User user = userOpt.get();

        // Kurangi kuota film
        film.setKuota(film.getKuota() - jumlahTiket);
        filmRepository.save(film);

        // Buat tiket dan simpan sebanyak jumlah tiket yang dibeli
        int hargaTotal = jumlahTiket * film.getHargaTiket();
        LocalDateTime waktuPembelian = LocalDateTime.now();
        for (int i = 0; i < jumlahTiket; i++) {
            Ticket ticket = new Ticket();
            ticket.setUser(user);
            ticket.setFilm(film);
            ticket.setHargaTotal(film.getHargaTiket());
            ticket.setWaktuPembelian(waktuPembelian);
            ticketRepository.save(ticket);
        }

        // Kembalikan struk sederhana
        return cetakStruk(user.getNama(), film.getNama(), jumlahTiket, film.getHargaTiket(), hargaTotal, waktuPembelian);
    }

    private String cetakStruk(String namaPembeli, String namaFilm, int jumlahTiket, int hargaTiket, int totalHarga, LocalDateTime waktu) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PEMBELIAN TIKET =====\n");
        sb.append("Nama Pembeli : ").append(namaPembeli).append("\n");
        sb.append("Film         : ").append(namaFilm).append("\n");
        sb.append("Jumlah Tiket : ").append(jumlahTiket).append("\n");
        sb.append("Harga Tiket  : Rp").append(hargaTiket).append("\n");
        sb.append("Total Bayar  : Rp").append(totalHarga).append("\n");
        sb.append("Waktu        : ").append(waktu).append("\n");
        sb.append("=================================\n");
        return sb.toString();
    }
}

