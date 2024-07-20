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
@RequestMapping("/v1")
public class AdministradorController {

    private final AdministradorServiceImp administradorServiceImp;

    public AdministradorController(AdministradorServiceImp administradorServiceImp) {
        this.administradorServiceImp = administradorServiceImp;
    }

    @PostMapping("/persona/{personaId}/administrador")
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

    @GetMapping("/administrador/{administradorId}")
    public ResponseEntity<Persona> getAdmById(@PathVariable String administradorId) {
        Persona adm = administradorServiceImp.findAdminById(administradorId);
        return ResponseEntity.ok().body(adm);
    }

    @GetMapping("/administrador")
    public ResponseEntity<PagedModel<Persona>> getAllAdm(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                         @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                         @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                         @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listAdm = administradorServiceImp.findAllAdm(pageRequest);
        PagedModel<Persona> pagedAdm = new PagedModel<>(listAdm);
        return ResponseEntity.ok().body(pagedAdm);
    }

    @PutMapping("/administrador/{administradorId}")
    public ResponseEntity<Persona> putPersonalById(@PathVariable String administradorId,
                                                   @RequestBody Persona adm) {
        Persona updatedAdm =  administradorServiceImp.updateAdm(administradorId, adm);
        return ResponseEntity.ok().body(updatedAdm);
    }

    @DeleteMapping("/administrador/{administradorId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable String administradorId) {
        administradorServiceImp.deleteAdm(administradorId);
        return ResponseEntity.accepted().build();
    }
}
