package com.project.mvc.model;

import java.time.LocalDate;      // Import class untuk merepresentasikan tanggal
import java.time.LocalTime;     // Import class untuk merepresentasikan waktu (jam tayang)

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
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

@Entity                        // Menandakan bahwa kelas ini adalah entitas JPA  
@Getter                       // Lombok untuk otomatis generate getter
@Setter                      // Lombok untuk otomatis generate setter
@NoArgsConstructor          // Lombok untuk otomatis generate constructor tanpa parameter
@AllArgsConstructor        // Lombok untuk otomatis generate constructor dengan semua parameter
@Table(name="schedules")  // Menentukan nama tabel dalam database yang akan digunakan untuk entitas ini

// Kelas Jadwal untuk merepresentasikan jadwal tayang film
public class Jadwal {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;    
    
    @JsonBackReference
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @Column(nullable = false)
    private  LocalTime jamTayang;

    @Column(nullable = false)
    private LocalDate tanggalTayang;
}
