package com.project.mvc.model;                           

// Importing LocalDateTime for time handling
import java.time.LocalDateTime;     

import com.fasterxml.jackson.annotation.JsonBackReference;    // Import untuk menghindari rekursi saat serialisasi JSON

import jakarta.persistence.Column;                           // Import untuk anotasi kolom pada JPA
import jakarta.persistence.Entity;                           // Import untuk anotasi entitas pada JPA
import jakarta.persistence.GeneratedValue;                   // Import untuk anotasi generasi nilai pada JPA
import jakarta.persistence.GenerationType;                   // Import untuk strategi generasi nilai pada JPA
import jakarta.persistence.Id;                              // Import untuk anotasi ID pada JPA
import jakarta.persistence.JoinColumn;                      // Import untuk anotasi kolom join pada JPA
import jakarta.persistence.ManyToOne;                       // Import untuk anotasi ManyToOne pada JPA
import jakarta.persistence.Table;                          // Import untuk anotasi tabel pada JPA
import lombok.AllArgsConstructor;                          // Import untuk anotasi Lombok yang meng-generate constructor dengan semua atribut
import lombok.Getter;                                     // Import untuk anotasi Lombok yang meng-generate getter methods
import lombok.NoArgsConstructor;                          // Import untuk anotasi Lombok yang meng-generate constructor tanpa parameter
import lombok.Setter;                                     // Import untuk anotasi Lombok yang meng-generate setter methods

@Entity                   // Menandakan bahwa kelas ini adalah entitas JPA
@Getter                   // Lombok anotasi untuk meng-generate getter methods
@Setter                   // Lombok anotasi untuk meng-generate setter methods
@NoArgsConstructor       // Lombok anotasi untuk meng-generate constructor tanpa parameter
@AllArgsConstructor      // Lombok anotasi untuk meng-generate constructor dengan semua field sebagai parameter
@Table(name="tickets")   // Menentukan nama tabel dalam database yang akan digunakan untuk entitas ini
public class Ticket {
    @Id                                                    // Menandakan bahwa field ini adalah primary key
    @GeneratedValue(strategy = GenerationType.UUID)        // Menggunakan UUID sebagai strategi generasi nilai untuk primary key
    private String id;                                     // Atribut untuk menyimpan ID unik dari tiket
 
    @ManyToOne(optional = false)                           // Many-to-One relationship dengan User
    @JoinColumn(name = "user_id", nullable = false)       // Menentukan kolom yang akan digunakan untuk relasi ini
    @JsonBackReference                                    // Menghindari masalah rekursi saat serialisasi JSON
    private User user;                                    // Atribut untuk menyimpan informasi pengguna yang membeli tiket

    @ManyToOne(optional = false)                          // Many-to-One relationship dengan Jadwal
    @JoinColumn(name="jadwal_id", nullable = false)       // Menentukan kolom yang akan digunakan untuk relasi ini
    private Jadwal jadwal;                                // Atribut untuk menyimpan informasi jadwal yang terkait dengan tiket

    @ManyToOne(optional = false)                         // Many-to-One relationship dengan Film
    @JoinColumn(name="film_id", nullable = false)        // Menentukan kolom yang akan digunakan untuk relasi ini
    private Film film;                                   // Atribut untuk menyimpan informasi film dalam tiket

    @Column(nullable = false)                            // Menandakan bahwa kolom ini tidak boleh bernilai null
    private int pembayaran;                              // Atribut untuk menyimpan jumlah pembayaran yang dilakukan

    @Column(nullable = false)                            // Menandakan bahwa kolom ini tidak boleh bernilai null
    private int kembalian;                               // Atribut untuk menyimpan jumlah kembalian yang diberikan

    @Column(nullable = false)                            // Menandakan bahwa kolom ini tidak boleh bernilai null
    private int kuantitas;                               // Atribut untuk menyimpan jumlah tiket yang dibeli

    @Column(nullable = false)                           // Menandakan bahwa kolom ini tidak boleh bernilai null
    private LocalDateTime waktuPembelian;               // Atribut untuk menyimpan waktu pembelian tiket
}
