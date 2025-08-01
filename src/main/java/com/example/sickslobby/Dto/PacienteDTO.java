package com.example.sickslobby.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PacienteDTO {

    @JsonProperty
    private String id;
    @JsonProperty
    private String Apellido;
    @JsonProperty
    private int Edad;
    @JsonProperty
    private String EstadoCivil;
    @JsonProperty
    private String  Nombre;
    @JsonProperty
    private String  Sexo;
    @JsonProperty
    private  String Telefono;
    @JsonProperty
    private String  Ocupacion;
}
