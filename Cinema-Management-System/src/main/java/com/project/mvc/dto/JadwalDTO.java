package com.project.mvc.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JadwalDTO {
    private String id;
    private LocalTime jamTayang;
    private LocalDate tanggalTayang;
    private FilmDTO film;
}
