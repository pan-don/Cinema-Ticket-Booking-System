package com.project.mvc.repository;

import com.project.mvc.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaksiRepository extends JpaRepository<Ticket, Long> {
    // Contoh method custom
    List<Ticket> findAllByNamaPembeli(String namaPembeli);
    // Tambahkan method lain sesuai kebutuhan
}
