package com.example.sickslobby.Dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CrearCitaDTO {
    private String especialistaId;
    private String pacienteId;
    private CitasDTO cita;

}
