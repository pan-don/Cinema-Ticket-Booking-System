package com.project.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.mvc.model.Ticket;
import com.project.mvc.model.User;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByUser(User user);
}