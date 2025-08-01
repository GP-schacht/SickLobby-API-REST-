package com.example.sickslobby.Services;

import com.example.sickslobby.Dto.PacienteDTO;

import java.util.List;

public interface PacientesServicesI {
    List <PacienteDTO> list();
    Boolean add(PacienteDTO post);
    Boolean edit(String id, PacienteDTO post);
    Boolean remove(String id);
}
