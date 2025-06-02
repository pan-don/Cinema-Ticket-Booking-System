package com.project.mvc.services;

import com.project.mvc.model.Film;
import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;
import com.project.mvc.repository.TicketRepository;


public class UserService {
 
    private final TicketRepository ticketRepository;

    public UserService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean processPayment(String paymentMethod, double amount) {
        // Logika pembayaran
        try {
            // Simulasi pemrosesan pembayaran
            System.out.println("Memproses pembayaran sebesar " + amount + " menggunakan " + paymentMethod);
            // TODO: Integrasikan dengan payment gateway yang sesuai (misalnya, Midtrans, Xendit, dll.)

            // Jika pembayaran berhasil
            return true;
        } catch (Exception e) {
            // Jika terjadi kesalahan saat pembayaran
            System.err.println("Terjadi kesalahan saat memproses pembayaran: " + e.getMessage());
            return false;
        }
    }

    public Ticket buyTicket(User user, Film film, int hargaTotal) {
        boolean paymentSuccess = processPayment("default", hargaTotal);
        if (!paymentSuccess) {
            System.out.println("Pembayaran gagal. Tiket tidak dapat dicetak.");
            return null;
        }
        // 2. Cetak tiket jika pembayaran berhasil
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFilm(film);
        ticket.setHargaTotal(hargaTotal);
        ticket.setWaktuPembelian(java.time.LocalDateTime.now());
        Ticket savedTicket = ticketRepository.save(ticket);
        System.out.println("Tiket berhasil dicetak: " + savedTicket);
        return savedTicket;
    }
}
