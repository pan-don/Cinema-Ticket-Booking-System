package com.project.mvc.controller;

import com.project.mvc.services.PembayaranService;

public class PembayaranController {
    private final PembayaranService pembayaranService;

    public PembayaranController(PembayaranService pembayaranService) {
        this.pembayaranService = pembayaranService;
    }

    // Method untuk menangani permintaan pembelian tiket dari view/user
    public String prosesPembelianTiket(String namaFilm, int jumlahTiket, String namaPembeli) {
        // Validasi input sederhana
        if (namaFilm == null || namaFilm.isEmpty() || namaPembeli == null || namaPembeli.isEmpty() || jumlahTiket <= 0) {
            return "Input tidak valid!";
        }
        // Panggil service untuk proses pembelian
        return pembayaranService.beliTiket(namaFilm, jumlahTiket, namaPembeli);
    }
}

