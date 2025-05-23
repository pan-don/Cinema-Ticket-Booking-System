package com.project.mvc.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="jadwal")
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(nullable = false)
    private  LocalTime jamTayang;

    @Column(nullable = false)
    private LocalDate tanggalTayang;
}
