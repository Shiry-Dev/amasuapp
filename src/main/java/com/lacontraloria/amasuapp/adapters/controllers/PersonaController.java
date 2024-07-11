package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.PersonaCreateService;
import com.lacontraloria.amasuapp.services.PersonaGetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona")
public class PersonaController {

    private final PersonaCreateService personaCreateService;
    private final PersonaGetService personaGetService;

    public PersonaController(PersonaCreateService personaCreateService,
                             PersonaGetService personaGetService) {
        this.personaCreateService = personaCreateService;
        this.personaGetService = personaGetService;
    }

    @PostMapping
    public ResponseEntity<Persona> createPersona(@RequestBody Persona reqBody) {
        Persona persona = personaCreateService.createPersona(reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(persona.getDniRieniec())
                        .toUri();
        return ResponseEntity.created(uri).body(persona);
    }

    @GetMapping("/{personaId}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long personaId) {
        Persona persona = personaGetService.findPersonaByDniReniec(personaId);
        return ResponseEntity.ok().body(persona);
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
