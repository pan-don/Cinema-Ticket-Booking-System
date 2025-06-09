package com.project.mvc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;
import com.project.mvc.services.LoginService;
import com.project.mvc.services.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final LoginService loginService;
    private final PaymentService paymentService;

    @PostMapping("/buy")
    public Ticket buyTicket(
        @RequestParam String username,
        @RequestParam String password,
        @RequestParam String jadwalId,
        @RequestParam int pembayaran,
        @RequestParam int kuantitas
    ) {
        User user = loginService.loginUser(username, password);
        return paymentService.buyTicket(user, jadwalId, pembayaran, kuantitas);
    }

    @PostMapping("/userTicket")
    public List<Ticket> showAllTicketByUser(
        @RequestParam String username,
        @RequestParam String password,
        @RequestBody User user
    ) {
        loginService.loginUser(username, password);
        return paymentService.getTicketByUser(user);
    }
}