package com.example.sickslobby.Dto;


import lombok.Data;
import org.jetbrains.annotations.NotNull;

@Data
public class CrearCitaDTO {
    @NotNull
    private String especialistaId;
    @NotNull
    private String pacienteId;
    private CitasDTO cita;

}
