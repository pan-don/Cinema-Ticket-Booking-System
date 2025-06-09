package com.project.mvc.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;    @Column(nullable = false, unique = true)
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
    private int tiketTerjual;
}