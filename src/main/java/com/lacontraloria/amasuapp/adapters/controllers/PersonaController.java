package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.AuthDTO;
import com.lacontraloria.amasuapp.adapters.dto.TokenDTO;
import com.lacontraloria.amasuapp.config.AuthorizationService;
import com.lacontraloria.amasuapp.config.TokenService;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class PersonaController {

    private final PersonaServiceImp personaServiceImp;
    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    public PersonaController(PersonaServiceImp personaServiceImp, AuthorizationService authorizationService,
                             AuthenticationManager authenticationManager, TokenService tokenService) {
        this.personaServiceImp = personaServiceImp;
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenService = tokenService;
    }

    @PostMapping("/persona/auth/login")
    public ResponseEntity<TokenDTO> authPersona(@RequestBody @Valid AuthDTO reqBody) {
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(reqBody.emailPrincipal(), reqBody.password());
        var auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var token = tokenService.generateToken((Persona) auth.getPrincipal());
        return ResponseEntity.ok().body(new TokenDTO(token));
    }

    @PostMapping("/persona")
    public ResponseEntity<Persona> createPersona(@RequestBody Persona reqBody) {
        Persona persona = personaServiceImp.createPersona(reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(persona.getDniRieniec())
                        .toUri();
        return ResponseEntity.created(uri).body(persona);
    }

    @GetMapping("/persona/{personaId}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable Long personaId) {
        Persona persona = personaServiceImp.findPersonaByDniReniec(personaId);
        return ResponseEntity.ok().body(persona);
    }

    @GetMapping("/persona")
    public ResponseEntity<PagedModel<Persona>> getAllPersona(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                             @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                             @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                             @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listPersona = personaServiceImp.getAllPersona(pageRequest);
        PagedModel<Persona> pagedPersona = new PagedModel<>(listPersona);
        return ResponseEntity.ok().body(pagedPersona);
    }

    @GetMapping("/persona/users")
    public ResponseEntity<PagedModel<Persona>> getAllUserPersona(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                                 @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                                 @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                                 @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listPersona = personaServiceImp.getAllUserPersona(pageRequest);
        PagedModel<Persona> pagedPersona = new PagedModel<>(listPersona);
        return ResponseEntity.ok().body(pagedPersona);
    }

    @PutMapping("/persona/{personaId}")
    public ResponseEntity<Persona> putPersonalById(@PathVariable Long personaId,
                                                   @RequestBody Persona persona) {
        Persona updatedPersona =  personaServiceImp.updatePersona(personaId, persona);
        return ResponseEntity.ok().body(updatedPersona);
    }

    @DeleteMapping("/persona/{personaId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable Long personaId) {
        personaServiceImp.deletePersona(personaId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/persona/{personaId}/curso")
    public ResponseEntity<Void> assignCurso(@PathVariable Long personaId,
                                            @RequestBody List<Long> cursoIds) {
        personaServiceImp.assignCursoToPersona(personaId, cursoIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/persona/{personaId}/alerta")
    public ResponseEntity<Void> assignAlerta(@PathVariable Long personaId,
                                             @RequestBody List<Long> cursoIds) {
        personaServiceImp.assignAlertaToPersona(personaId, cursoIds);
        return ResponseEntity.ok().build();
    }
}
