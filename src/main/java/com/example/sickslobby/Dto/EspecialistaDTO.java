package com.example.sickslobby.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EspecialistaDTO {

    private String id;
    @JsonProperty
    private String Apellido;
    @JsonProperty
    private int Edad;
    @JsonProperty
    private String Especialidad;
    @JsonProperty
    private String Nombre;
}
