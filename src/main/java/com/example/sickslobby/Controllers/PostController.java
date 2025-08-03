package com.example.sickslobby.Controllers;

import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Dto.PacienteDTO;
import com.example.sickslobby.Services.EspecialistaServicesI;
import com.example.sickslobby.Services.PacientesServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {



    @Autowired
    private PacientesServicesI servicioPaciente;

    @Autowired
    private EspecialistaServicesI servicioEspecialista;



    @GetMapping(value = "/greet")
    public String greet() {

        return "Hello world";
    }

    @GetMapping(value = "/List")
    public ResponseEntity listPacientes() {
        return new ResponseEntity(servicioPaciente.list(), HttpStatus.OK);
    }

// add

    //pacientes
    @PostMapping(value = "/addPaciente")
    public ResponseEntity add(@RequestBody PacienteDTO post){
        System.out.println(post);
        return new ResponseEntity(servicioPaciente.add(post), HttpStatus.OK);
}

    //Especialistas

    @GetMapping(value = "/ListEspecialista")
    public ResponseEntity listEspecialistas(){
        return new ResponseEntity(servicioEspecialista.list(), HttpStatus.OK);
    }

    @PostMapping(value = "/addEspecialista")
    public ResponseEntity add(@RequestBody EspecialistaDTO post){
        return new ResponseEntity(servicioEspecialista.add(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity update(@RequestBody PacienteDTO post, @PathVariable (value = "id") String id) {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable (value = "id") String id) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
