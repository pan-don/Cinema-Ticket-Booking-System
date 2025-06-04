package com.project.mvc.services;

import com.project.mvc.models.Film;
import com.project.mvc.models.Transaksi;
import com.project.mvc.repositories.FilmRepository;
import com.project.mvc.repositories.TransaksiRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class PembayaranService {
    private final FilmRepository filmRepository;
    private final TransaksiRepository transaksiRepository;

    public PembayaranService(FilmRepository filmRepository, TransaksiRepository transaksiRepository) {
        this.filmRepository = filmRepository;
        this.transaksiRepository = transaksiRepository;
    }

    // Mengelola proses pembelian film, pengecekan kuota, pencatatan transaksi, dan pembuatan struk
    public String beliTiket(String namaFilm, int jumlahTiket, String namaPembeli) {
        // Cari film berdasarkan nama
        Optional<Film> filmOpt = filmRepository.findByNama(namaFilm);
        if (filmOpt.isEmpty()) {
            return "Film tidak ditemukan!";
        }
        Film film = filmOpt.get();

        // Cek ketersediaan kursi/kuota
        if (film.getKuota() < jumlahTiket) {
            return "Kuota tidak mencukupi! Sisa kuota: " + film.getKuota();
        }

        // Kurangi kuota film
        Film.setKuota(film.getKuota() - jumlahTiket);
        FilmRepository.save(film);

        // Catat transaksi
        Transaksi transaksi = new Transaksi();
        transaksi.setNamaPembeli(namaPembeli);
        transaksi.setFilm(film);
        transaksi.setJumlahTiket(jumlahTiket);
        transaksi.setTotalHarga(jumlahTiket * film.getHargaTiket());
        transaksi.setWaktuTransaksi(LocalDateTime.now());
        transaksiRepository.save(transaksi);

        // Cetak struk/tiket
        return cetakStruk(transaksi);
    }

    // Membuat struk transaksi
    private String cetakStruk(Transaksi transaksi) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== STRUK PEMBELIAN TIKET =====\n");
        sb.append("Nama Pembeli : ").append(transaksi.getNamaPembeli()).append("\n");
        sb.append("Film         : ").append(transaksi.getFilm().getNama()).append("\n");
        sb.append("Jumlah Tiket : ").append(transaksi.getJumlahTiket()).append("\n");
        sb.append("Harga Tiket  : Rp").append(transaksi.getFilm().getHargaTiket()).append("\n");
        sb.append("Total Bayar  : Rp").append(transaksi.getTotalHarga()).append("\n");
        sb.append("Waktu        : ").append(transaksi.getWaktuTransaksi()).append("\n");
        sb.append("=================================\n");
        return sb.toString();
    }
}

