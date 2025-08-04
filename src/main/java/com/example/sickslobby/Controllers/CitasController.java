package com.example.sickslobby.Controllers;

import com.example.sickslobby.Dto.CitasDTO;
import com.example.sickslobby.Dto.CrearCitaDTO;
import com.example.sickslobby.Services.CitasServicesI;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitasController {

    @Autowired
    private final CitasServicesI citasServices;

    public CitasController(CitasServicesI citasServices) {
        this.citasServices = citasServices;
    }


    @PostMapping(value = "/addCita")
    public ResponseEntity<String> addCita(@RequestBody CrearCitaDTO citas) {
        Boolean respuesta = citasServices.add(citas.getCita(), citas.getEspecialistaId(), citas.getPacienteId());
        System.out.println("Citas: " + citas);

        return ResponseEntity.ok("Cita creada.");

    }

    @GetMapping("/list")
    public ResponseEntity getCitas() {
        return ResponseEntity.ok(citasServices.list());
    }

    @GetMapping("/id")
    public ResponseEntity getById(
            @RequestParam String id,
            @RequestParam String especialistaId) {
        return ResponseEntity.ok(citasServices.getById(id, especialistaId));
    }
}
