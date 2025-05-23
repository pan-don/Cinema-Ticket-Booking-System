package com.project.mvc.repository;

import com.project.mvc.model.User;
import com.project.mvc.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, String> {
    List<Ticket> findByUser(User user);
}
