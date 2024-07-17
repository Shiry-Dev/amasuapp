package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.AdministradorServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/administrador")
public class AdministradorController {

    private final AdministradorServiceImp administradorServiceImp;

    public AdministradorController(AdministradorServiceImp administradorServiceImp) {
        this.administradorServiceImp = administradorServiceImp;
    }

    @PostMapping
    public ResponseEntity<Persona> createAdm(@PathVariable Long personaId,
                                             @RequestBody Persona reqBody) {
        Persona adm = administradorServiceImp.createAdm(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(adm.getIdAdministrador())
                        .toUri();
        return ResponseEntity.created(uri).body(adm);
    }

    @GetMapping("/{administradorId}")
    public ResponseEntity<Persona> getAdmById(@PathVariable Long personaId,
                                                    @PathVariable Long administradorId) {
        Persona adm = administradorServiceImp.findAdminById(personaId, administradorId);
        return ResponseEntity.ok().body(adm);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Persona>> getAllAdm(@PathVariable Long personaId,
                                                         @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                         @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                         @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                         @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listAdm = administradorServiceImp.findAllAdm(personaId, pageRequest);
        PagedModel<Persona> pagedAdm = new PagedModel<>(listAdm);
        return ResponseEntity.ok().body(pagedAdm);
    }

    @PutMapping("/{administradorId}")
    public ResponseEntity<Persona> putPersonalById(@PathVariable Long personaId,
                                                   @PathVariable Long administradorId,
                                                   @RequestBody Persona adm) {
        Persona updatedAdm =  administradorServiceImp.updateAdm(personaId, administradorId, adm);
        return ResponseEntity.ok().body(updatedAdm);
    }

    @DeleteMapping("/{administradorId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable Long personaId,
                                                  @PathVariable Long administradorId) {
        administradorServiceImp.deleteAdm(personaId, administradorId);
        return ResponseEntity.accepted().build();
    }
}
