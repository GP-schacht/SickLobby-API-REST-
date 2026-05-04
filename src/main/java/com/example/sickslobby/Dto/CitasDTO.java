package com.example.sickslobby.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CitasDTO {

    private String id;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "La fecha de la cita es obligatoria")
    private String fechaCita;

    private String especialistaId;

    private String pacienteId;

    private String nombrePaciente;
}
