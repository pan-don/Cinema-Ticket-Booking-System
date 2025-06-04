package com.project.mvc.services;

import java.util.List;

import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;

public interface TicketService {
    Ticket createTicket(Ticket ticket);
    List<Ticket> getAllTickets();
    List<Ticket> getTicketsByUser(User user);
}