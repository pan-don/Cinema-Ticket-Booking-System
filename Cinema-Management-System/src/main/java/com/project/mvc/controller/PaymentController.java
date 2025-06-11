package com.project.mvc.controller;

// Import untuk List yang digunakan untuk daftar tiket
import java.util.List;

import org.springframework.http.HttpStatus;                      // Import untuk status HTTP
import org.springframework.http.ResponseEntity;                  // Import untuk ResponseEntity
import org.springframework.web.bind.annotation.PostMapping;      // Import untuk anotasi PostMapping
import org.springframework.web.bind.annotation.RequestMapping;   // Import untuk anotasi RequestMapping
import org.springframework.web.bind.annotation.RequestParam;     // Import untuk anotasi RequestParam
import org.springframework.web.bind.annotation.RestController;   // Import untuk anotasi RestController

import com.project.mvc.model.Ticket;                  // Import model Ticket 
import com.project.mvc.model.User;                   // Import model User
import com.project.mvc.services.LoginService;       // Import service LoginService
import com.project.mvc.services.PaymentService;    // Import service PaymentService

import lombok.RequiredArgsConstructor;            // Import untuk anotasi Lombok yang meng-generate constructor dengan semua atribut sebagai parameter

@RestController                                  // Menandakan bahwa kelas ini adalah controller REST
@RequestMapping("/api/payments")                // Menentukan base URL untuk semua endpoint dalam controller ini
@RequiredArgsConstructor                       // Menggunakan Lombok untuk meng-generate constructor dengan parameter final

// Controller untuk menangani pembayaran dan tiket
public class PaymentController { 
    private final LoginService loginService;
    private final PaymentService paymentService;
 
    // Untuk membeli tiket
    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId,
        @RequestParam int pembayaran,
        @RequestParam int kuantitas
    ) {
        User user = loginService.loginUser(username, password);
        Ticket createdTicket = paymentService.buyTicket(user, jadwalId, pembayaran, kuantitas);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    // Untuk menampilkan semua tiket yang dibeli oleh pengguna
    @PostMapping("/userTicket")
    public ResponseEntity<List<Ticket>> showAllTicketByUser(
        @RequestParam String username,
        @RequestParam String password
    ) {
        User user = loginService.loginUser(username, password);
        List<Ticket> tickets = paymentService.getTicketByUser(user);
        return ResponseEntity.ok(tickets);
    }
}