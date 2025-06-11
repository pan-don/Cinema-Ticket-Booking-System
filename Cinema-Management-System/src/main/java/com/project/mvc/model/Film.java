package com.project.mvc.model;

// Import class untuk merepresentasikan waktu (durasi)
import java.time.LocalTime;        

import jakarta.persistence.Column;              // Import untuk anotasi kolom pada JPA
import jakarta.persistence.Entity;              // Import untuk anotasi entitas pada JPA
import jakarta.persistence.GeneratedValue;      // Import untuk anotasi generasi nilai pada JPA
import jakarta.persistence.GenerationType;      // Import untuk strategi generasi nilai pada JPA
import jakarta.persistence.Id;                  // Import untuk anotasi ID pada JPA
import jakarta.persistence.Table;               // Import untuk anotasi tabel pada JPA
import lombok.AllArgsConstructor;               // Import untuk anotasi Lombok yang meng-generate constructor dengan semua atribut sebagai parameter
import lombok.Getter;                           // Import untuk anotasi Lombok yang meng-generate getter methods
import lombok.NoArgsConstructor;                // Import untuk anotasi Lombok yang meng-generate constructor tanpa parameter
import lombok.Setter;                           // Import untuk anotasi Lombok yang meng-generate setter methods

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
    private LocalTime durasi;  // Menggunakan LocalTime untuk durasi film

    @Column(nullable = false)
    private String ruangan;    // Nama ruangan tempat film diputar

    @Column(nullable = false)
    private int kapasitasRuangan;   // Kapasitas ruangan untuk penonton
    
    @Column(nullable = false)
    private int harga;          // Harga tiket untuk film ini
    
    @Column(nullable = false)
    private int tiketTerjual;    // Jumlah tiket yang sudah terjual
}