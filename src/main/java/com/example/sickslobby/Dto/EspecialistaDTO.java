package com.example.sickslobby.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EspecialistaDTO {

    private String id;

    private String apellido;

    private Integer edad;

    private String especialidad;

    private String nombre;


}
