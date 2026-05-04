package com.example.sickslobby.Controllers;

import com.example.sickslobby.Dto.CitasDTO;
import com.example.sickslobby.Dto.CrearCitaDTO;
import com.example.sickslobby.Services.CitasServicesI;
import com.example.sickslobby.Services.use.CitasServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitasController {

    private final CitasServicesI citasServices;
    private final CitasServices citasServiceImpl;

    public CitasController(CitasServicesI citasServices, CitasServices citasServiceImpl) {
        this.citasServices = citasServices;
        this.citasServiceImpl = citasServiceImpl;
    }


    @PostMapping(value = "/addCita")
    public ResponseEntity<String> addCita(@RequestBody @Valid CrearCitaDTO citas) {
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

    @PutMapping("/edit")
    public ResponseEntity<Boolean> editCita(
            @RequestParam String id,
            @RequestBody @Valid CitasDTO cita) {
        return ResponseEntity.ok(citasServices.edit(id, cita));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteCita(
            @RequestParam String id,
            @RequestParam String especialistaId) {
        return ResponseEntity.ok(citasServiceImpl.remove(id, especialistaId));
    }
}
