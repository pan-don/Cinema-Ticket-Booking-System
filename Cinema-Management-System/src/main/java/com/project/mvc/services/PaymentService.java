package com.project.mvc.services;

import java.lang.module.ResolutionException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.mvc.model.Film;
import com.project.mvc.model.Jadwal;
import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;
import com.project.mvc.repository.JadwalRepository;
import com.project.mvc.repository.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final JadwalRepository jadwalRepo;
    private final TicketRepository ticketRepo;    @Transactional
    public Ticket buyTicket(User user, String jadwalId, int pembayaran, int kuantitas) {
        if (kuantitas <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Jadwal jadwal = jadwalRepo.findById(jadwalId)
            .orElseThrow(() -> new ResolutionException("Schedule not found"));
        
        Film film = jadwal.getFilm();
        if (film.getKapasitasRuangan() < kuantitas) {
            throw new RuntimeException("Not enough seats available");
        }

        int totalHarga = film.getHarga() * kuantitas;
        if (pembayaran < totalHarga) {
            throw new RuntimeException("Insufficient payment. Required: " + totalHarga);
        }
        
        int kembalian = pembayaran - totalHarga;
        
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setJadwal(jadwal);
        ticket.setFilm(film);
        ticket.setPembayaran(pembayaran);
        ticket.setKembalian(kembalian);
        ticket.setKuantitas(kuantitas);
        ticket.setWaktuPembelian(java.time.LocalDateTime.now());

        return ticketRepo.save(ticket);
    }
    
    public List<Ticket> getTicketByUser(User user) {
        return ticketRepo.findByUser(user);
    }

    @Transactional
    public int processPayment(int moneyPaid, Film film, int kuantitas) {
        return moneyPaid - (film.getHarga() * kuantitas);
    }
}