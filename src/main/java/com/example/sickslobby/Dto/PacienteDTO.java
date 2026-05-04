package com.example.sickslobby.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PacienteDTO {


    private String id;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La edad es obligatoria")
    private Integer edad;

    private String estadoCivil;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    private String Sexo;

    private String grupoEdad;

    @Size(max = 20, message = "El telefono no puede tener mas de 20 caracteres")
    private String telefono;

    private String ocupacion;

    public String getGrupoEdad() {
        return obtenerGrupoEdad();
    }

    private String obtenerGrupoEdad() {
        if (edad == null) {
            return "Desconocido";
        }

        if (edad >= 0 && edad <= 1) {
            return "Neo nato";
        } else if (edad >= 2 && edad <= 17) {
            return "Niño";
        } else if (edad >= 18 && edad <= 59) {
            return "Adulto";
        } else if (edad >= 60 && edad <= 119) {
            return "Anciano";
        } else if (edad > 120) {
            return "Edad no valida";
        } else {
            return "Desconocido";
        }
    }


}
