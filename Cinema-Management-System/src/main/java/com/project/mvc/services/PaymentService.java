package com.project.mvc.services;

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
    private final TicketRepository ticketRepo;

    @Transactional
    public Ticket buyTicket(User user, String jadwalId, int pembayaran) {
        Jadwal jadwal = jadwalRepo.findById(jadwalId)
        .orElseThrow(() -> new RuntimeException("Jadwal not found"));
        
        int kembalian = processPayment(pembayaran, jadwal.getFilm());
        if(kembalian < 0){
            throw new RuntimeException("Sorry, you are short "+kembalian);
        }
        
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setJadwal(jadwal);
        ticket.setFilm(jadwal.getFilm());
        ticket.setPembayaran(pembayaran);
        ticket.setKembalian(kembalian);
        ticket.setWaktuPembelian(java.time.LocalDateTime.now());

        return ticketRepo.save(ticket);
    }
    
    public List<Ticket> getTicketByUser(User user) {
        return ticketRepo.findByUser(user);
    }

    public int processPayment(int moneyPaid, Film film){
        return moneyPaid - film.getHarga();
    }
}