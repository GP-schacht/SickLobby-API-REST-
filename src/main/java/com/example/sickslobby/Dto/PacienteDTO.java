package com.example.sickslobby.Dto;

import lombok.Data;

@Data
public class PacienteDTO {


    private String id;

    private String apellido;

    private int edad;

    private String estadoCivil;

    private String nombre;

    private String Sexo;

    private String grupoEdad;

    private String telefono;

    private String ocupacion;

    public String getGrupoEdad() {
        return obtenerGrupoEdad();
    }

    private String obtenerGrupoEdad() {
        if (edad >= 0 && edad <= 1) {
            return "Neo nato";
        } else if (edad >= 2 && edad <= 17) {
            return "Niño";
        } else if (edad >= 18 && edad <= 59) {
            return "Adulto";
        } else if (edad >= 60 && edad <= 119) {
            return "Anciano";
        } else if (edad > 120) {
            throw new IllegalArgumentException("Nadie es tan viejo");
        } else {
            return null;
        }

    }


}


