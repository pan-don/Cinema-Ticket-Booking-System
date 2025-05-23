package com.project.mvc.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="film_id", nullable = false)
    private Film film;

    @Column(nullable = false)
    private int hargaTotal;

    @Column(nullable = false)
    private LocalDateTime waktuPembelian;
}
