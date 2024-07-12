package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.PersonaCreateService;
import com.lacontraloria.amasuapp.services.PersonaDeleteService;
import com.lacontraloria.amasuapp.services.PersonaGetService;
import com.lacontraloria.amasuapp.services.PersonaUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona")
public class PersonaController {

    private final PersonaCreateService personaCreateService;
    private final PersonaGetService personaGetService;
    private final PersonaUpdateService personaUpdateService;
    private final PersonaDeleteService personaDeleteService;

    public PersonaController(PersonaCreateService personaCreateService,
                             PersonaGetService personaGetService,
                             PersonaUpdateService personaUpdateService,
                             PersonaDeleteService personaDeleteService) {
        this.personaCreateService = personaCreateService;
        this.personaGetService = personaGetService;
        this.personaUpdateService = personaUpdateService;
        this.personaDeleteService = personaDeleteService;
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
    public ResponseEntity<Persona> putPersonalById(@PathVariable Long personaId,
                                                   @RequestBody Persona persona) {
        Persona updatedPersona =  personaUpdateService.updatePersona(persona);
        return ResponseEntity.ok().body(updatedPersona);
    }

    @DeleteMapping("/{personaId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable Long personaId) {
        personaDeleteService.deletePersona(personaId);
        return ResponseEntity.accepted().build();
    }
}
