package com.project.mvc.model;

import java.time.LocalTime;            // Import class untuk merepresentasikan waktu (durasi)

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

@Entity // Menandakan bahwa kelas ini adalah entitas JPA
@Getter // Lombok untuk otomatis generate getter
@Setter // Lombok untuk otomatis generate setter
@NoArgsConstructor // Lombok untuk otomatis generate constructor tanpa parameter
@AllArgsConstructor // Lombok untuk otomatis generate constructor dengan semua parameter
@Table(name="films") // Menentukan nama tabel dalam database yang akan digunakan untuk entitas ini
public class Film {
    @Id // Menandakan bahwa field ini adalah primary key
    @GeneratedValue(strategy = GenerationType.UUID) // Menghasilkan nilai unik secara otomatis
    private String id;    @Column(nullable = false, unique = true)
    private String judul;

    @Column(nullable = false) // Menandakan bahwa kolom ini tidak boleh null
    private String genre;     // Genre film, misalnya Action, Drama, dll.

    @Column(nullable = false)
    private String sinopsis;   // Sinopsis atau ringkasan film

    @Column(nullable = false)
    private LocalTime durasi;    // Menggunakan LocalTime untuk durasi film

    @Column(nullable = false)
    private String ruangan;      // Nama ruangan tempat film diputar

    @Column(nullable = false)
    private int kapasitasRuangan;   // Kapasitas ruangan untuk penonton
    
    @Column(nullable = false)
    private int harga;          // Harga tiket untuk film ini
    
    @Column(nullable = false)
    private int tiketTerjual;    // Jumlah tiket yang sudah terjual
}