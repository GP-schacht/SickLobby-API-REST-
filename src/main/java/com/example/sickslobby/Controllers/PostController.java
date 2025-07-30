package com.example.sickslobby.Controllers;

import com.example.sickslobby.PostDTO;
import com.example.sickslobby.Services.SicksLobbyServicesI;
import com.example.sickslobby.Services.use.SicksLobbyServicesUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private SicksLobbyServicesI servicio;

    @GetMapping(value = "/greet")
    public String greet() {

        return "Hello world";
    }

    @GetMapping(value = "/List")
    public ResponseEntity list() {
    return new ResponseEntity(null, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity add(@RequestBody PostDTO post){
        return new ResponseEntity(servicio.add(post), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity update(@RequestBody PostDTO post, @PathVariable (value = "id") String id) {
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity delete(@PathVariable (value = "id") String id) {
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
