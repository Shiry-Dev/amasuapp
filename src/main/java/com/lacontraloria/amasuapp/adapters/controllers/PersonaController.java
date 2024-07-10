package com.lacontraloria.amasuapp.adapters.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/persona")
public class PersonaController {

    public PersonaController() {
    }

    @GetMapping("/{personaId}")
    public ResponseEntity<Void> getPersonaById(@PathVariable String personaId) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> getAllPersona(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                       @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                       @RequestParam (value = "sort", defaultValue = "", required = false) String sort,
                                       @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{personaId}")
    public ResponseEntity<Void> putPersonalById(@PathVariable String personaId) {
        return ResponseEntity.ok().build();
    }
}
