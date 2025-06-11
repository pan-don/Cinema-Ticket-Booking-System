package com.project.mvc.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilmDTO {
    private String id;
    private String judul;
    private String genre;
    private String sinopsis;
    private LocalTime durasi;
    private String ruangan;
    private int kapasitasRuangan;
    private int harga;
}