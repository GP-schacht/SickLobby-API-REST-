package com.example.sickslobby.Services;

import com.example.sickslobby.Dto.CitasDTO;
import com.example.sickslobby.Dto.CrearCitaDTO;

import java.util.List;

public interface CitasServicesI {

    /**
     * Lista todas las citas (puede ajustarse para listar por especialista si lo deseas).
     */
    List<CitasDTO> list();

    CrearCitaDTO getById(String id, String especialistaId);


    Boolean add(CitasDTO cita, String especialistaID, String pacienteID);


    Boolean edit(String id, CitasDTO post);



    Boolean remove(String id);
}

