package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.services.AlertaServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/alerta")
public class AlertaController {

    private final AlertaServiceImp alertaServiceImp;

    public AlertaController(AlertaServiceImp alertaServiceImp) {
        this.alertaServiceImp = alertaServiceImp;
    }

    @PostMapping
    public ResponseEntity<Alerta> createAlerta(@PathVariable Long personaId,
                                               @RequestBody Alerta reqBody) {
        Alerta alerta = alertaServiceImp.createAlerta(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(alerta.getIdAlerta())
                .toUri();
        return ResponseEntity.created(uri).body(alerta);
    }

    @GetMapping("/{alertaId}")
    public ResponseEntity<Alerta> getAlertaById(@PathVariable Long personaId,
                                                @PathVariable Long alertaId) {
        Alerta alerta = alertaServiceImp.findByAlertaId(personaId, alertaId);
        return ResponseEntity.ok().body(alerta);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Alerta>> getAllAlerta(@PathVariable Long personaId,
                                                           @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                           @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                           @RequestParam (value = "sort", defaultValue = "idAlerta", required = false) String sort,
                                                           @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction,
                                                           @RequestParam (value = "cui", required = false) Long cui){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Alerta> listAlerta = alertaServiceImp.getAllAlerta(personaId, cui, pageRequest);
        PagedModel<Alerta> pagedAlerta = new PagedModel<>(listAlerta);
        return ResponseEntity.ok().body(pagedAlerta);
    }

    @PutMapping("/{alertaId}")
    public ResponseEntity<Alerta> putAlertaId(@PathVariable Long personaId,
                                              @PathVariable Long alertaId,
                                              @RequestBody Alerta alerta) {
        Alerta updatedAlerta =  alertaServiceImp.updateAlerta(personaId, alertaId, alerta);
        return ResponseEntity.ok().body(updatedAlerta);
    }

    @DeleteMapping("/{alertaId}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Long personaId,
                                             @PathVariable Long alertaId) {
        alertaServiceImp.deleteAlerta(personaId, alertaId);
        return ResponseEntity.accepted().build();
    }
}
