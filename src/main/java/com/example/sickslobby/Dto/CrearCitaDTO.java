package com.example.sickslobby.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CrearCitaDTO {
    @NotBlank(message = "El ID del especialista es obligatorio")
    private String especialistaId;
    
    @NotBlank(message = "El ID del paciente es obligatorio")
    private String pacienteId;
    
    @NotNull(message = "La cita es obligatoria")
    @Valid
    private CitasDTO cita;

}
