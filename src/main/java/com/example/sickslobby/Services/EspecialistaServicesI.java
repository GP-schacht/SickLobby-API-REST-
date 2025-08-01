package com.example.sickslobby.Services;

import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Dto.PacienteDTO;

import java.util.List;

public interface EspecialistaServicesI {

    List<EspecialistaDTO> list();
    Boolean add(EspecialistaDTO post);
    Boolean edit(String id, EspecialistaDTO post);
    Boolean remove(String id);
}
