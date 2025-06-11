package com.project.mvc.services;

import java.time.LocalDate;
import java.time.LocalTime;
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
    
    // validasi untuk data integer
    private void validateNumber(String fieldName, int value) {
        if (value < 0) {
            throw new RuntimeException(fieldName + " cannot be negative");
        }
    }
    
    @Transactional
    public Ticket buyTicket(User user, String jadwalId, int pembayaran, int kuantitas) {
        validateNumber("Payment amount", pembayaran);
        validateNumber("Quantity", kuantitas);

        // Get schedule and validate it exists
        Jadwal jadwal = jadwalRepo.findById(jadwalId)
            .orElseThrow(() -> new RuntimeException("Schedule not found"));
        
        // Get film from schedule
        Film film = jadwal.getFilm();
        
        // Validate schedule hasn't passed
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();
        
        if (jadwal.getTanggalTayang().isBefore(today) || 
            (jadwal.getTanggalTayang().equals(today) && jadwal.getJamTayang().isBefore(now))) {
            throw new RuntimeException("This schedule has already passed");
        }

        // Check ticket availability
        List<Ticket> soldTickets = ticketRepo.findByJadwal(jadwal);
        int totalSoldSeats = soldTickets.stream()
            .mapToInt(Ticket::getKuantitas)
            .sum();
            
        int remainingSeats = film.getKapasitasRuangan() - totalSoldSeats;
        
        if (remainingSeats < kuantitas) {
            throw new RuntimeException("Not enough seats available. Only " + remainingSeats + " seats left");
        }

        // Validate payment amount
        int totalHarga = film.getHarga() * kuantitas;
        if (pembayaran < totalHarga) {
            throw new RuntimeException("Insufficient payment. Required: Rp " + totalHarga);
        }
        
        // Calculate change
        int kembalian = pembayaran - totalHarga;
        
        // Create and save ticket
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setJadwal(jadwal);
        ticket.setJudulFilm(film.getJudul());
        ticket.setRuanganFilm(film.getRuangan());
        ticket.setPembayaran(pembayaran);
        ticket.setKembalian(kembalian);
        ticket.setKuantitas(kuantitas);
        ticket.setTotalHarga(totalHarga);
        ticket.setWaktuPembelian(java.time.LocalDateTime.now());

        return ticketRepo.save(ticket);
    }
    
    public List<Ticket> getTicketByUser(User user) {
        return ticketRepo.findByUser(user);
    }

    @Transactional
    public int processPayment(int moneyPaid, Film film, int kuantitas) {
        validateNumber("Payment amount", moneyPaid);
        validateNumber("Quantity", kuantitas);

        int totalHarga = film.getHarga() * kuantitas;
        if (moneyPaid < totalHarga) {
            throw new RuntimeException("Insufficient payment amount");
        }
        return moneyPaid - totalHarga;
    }
}