package com.project.mvc.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name="jadwal_id", nullable = false)
    private Jadwal jadwal;

    @Column(nullable = false)
    private String judulFilm;

    @Column(nullable = false)
    private  String ruanganFilm;

    @Column(nullable = false)
    private int pembayaran; 

    @Column(nullable = false)
    private int kembalian;

    @Column(nullable = false)
    private int kuantitas;

    @Column(nullable = false)
    private int totalHarga;

    @Column(nullable = false)
    private LocalDateTime waktuPembelian;
}
