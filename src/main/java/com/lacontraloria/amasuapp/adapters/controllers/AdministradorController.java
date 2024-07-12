package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Administrador;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.AdministradorServiceImp;
import com.lacontraloria.amasuapp.services.PersonaServiceImp;
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
    public ResponseEntity<Administrador> createAdm(@PathVariable Long personaId,
                                                   @RequestBody Administrador reqBody) {
        Administrador adm = administradorServiceImp.createAdm(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(adm.getDniAdministrador())
                        .toUri();
        return ResponseEntity.created(uri).body(adm);
    }

    @GetMapping("/{administradorId}")
    public ResponseEntity<Administrador> getAdmById(@PathVariable Long personaId,
                                                    @PathVariable Long administradorId) {
        Administrador adm = administradorServiceImp.findAdminById(personaId, administradorId);
        return ResponseEntity.ok().body(adm);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Administrador>> getAllAdm(@PathVariable Long personaId,
                                                               @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                               @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                               @RequestParam (value = "sort", defaultValue = "dniAdministrador", required = false) String sort,
                                                               @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Administrador> listAdm = administradorServiceImp.findAllAdm(personaId, pageRequest);
        PagedModel<Administrador> pagedAdm = new PagedModel<>(listAdm);
        return ResponseEntity.ok().body(pagedAdm);
    }

    @PutMapping("/{administradorId}")
    public ResponseEntity<Administrador> putPersonalById(@PathVariable Long personaId,
                                                         @PathVariable Long administradorId,
                                                         @RequestBody Administrador adm) {
        Administrador updatedAdm =  administradorServiceImp.updateAdm(personaId, administradorId, adm);
        return ResponseEntity.ok().body(updatedAdm);
    }

    @DeleteMapping("/{administradorId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable Long personaId,
                                                  @PathVariable Long administradorId) {
        administradorServiceImp.deleteAdm(personaId, administradorId);
        return ResponseEntity.accepted().build();
    }
}
