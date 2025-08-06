package com.example.sickslobby.Controllers;

import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Dto.PacienteDTO;
import com.example.sickslobby.Services.ServicesInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {



    @Autowired
    private ServicesInterface<PacienteDTO> servicioPaciente ;
    @Autowired
    private ServicesInterface<EspecialistaDTO> servicioEspecialista  ;




    @GetMapping(value = "/ListPaciente")
    public ResponseEntity listPacientes() {
        return new ResponseEntity(servicioPaciente.list(), HttpStatus.OK);
    }

    //pacientes
    @PostMapping(value = "/addPaciente")
    public ResponseEntity add(@RequestBody PacienteDTO post){
        System.out.println(post);
        return new ResponseEntity(servicioPaciente.add(post), HttpStatus.OK);
}

    @PutMapping(value = "/editPaciente")
    public ResponseEntity edit(@RequestBody PacienteDTO post,
                               @RequestParam String id) {

        try {
            return new ResponseEntity<>(servicioPaciente.edit(id, post), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping(value = "/deletePaciente")
    public ResponseEntity deletePaciente(@RequestParam  String id) {
        return new ResponseEntity(servicioPaciente.remove(id), HttpStatus.OK);
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

    @PutMapping(value = "/editEspecialista")
    public ResponseEntity editEspecialista(@RequestBody EspecialistaDTO post,
                               @RequestParam  String id) {
        try {
            return new ResponseEntity<>(servicioEspecialista.edit(id, post), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/deleteEspecialista")
    public ResponseEntity deleteEspecialista(@RequestParam  String id) {
        return new ResponseEntity(servicioEspecialista.remove(id), HttpStatus.OK);
    }
}
