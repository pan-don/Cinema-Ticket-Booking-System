package com.project.mvc.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name="film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String judul;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String sinopsis;

    @Column(nullable = false)
    private LocalTime durasi;

    @Column(nullable = false)
    private String ruangan;

    @Column(nullable = false)
    private int kapasitasRuangan;
    
    @Column(nullable = false)
    private int harga;
    
    @Column(nullable = false)
    private String tiketTerjual;
}
