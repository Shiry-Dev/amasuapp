package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Observacion;
import com.lacontraloria.amasuapp.services.ObservacionServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/alerta/{alertaId}/observacion")
public class ObservacionController {

    private final ObservacionServiceImp observacionServiceImp;

    public ObservacionController(ObservacionServiceImp observacionServiceImp) {
        this.observacionServiceImp = observacionServiceImp;
    }

    @PostMapping
    public ResponseEntity<Observacion> createPersona(@PathVariable Long personaId,
                                                     @PathVariable Long alertaId,
                                                     @RequestBody Observacion reqBody) {
        Observacion observacion = observacionServiceImp.createObservacion(personaId, alertaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(observacion.getIdObservacion())
                .toUri();
        return ResponseEntity.created(uri).body(observacion);
    }

    @GetMapping("/{observacionId}")
    public ResponseEntity<Observacion> getObservacion(@PathVariable Long personaId,
                                                      @PathVariable Long alertaId,
                                                      @PathVariable Long observacionId) {
        Observacion observacion = observacionServiceImp.findByObservacion(personaId, observacionId);
        return ResponseEntity.ok().body(observacion);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Observacion>> getAllObservacion(@PathVariable Long personaId,
                                                                     @PathVariable Long alertaId,
                                                                     @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                                     @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                                     @RequestParam (value = "sort", defaultValue = "idObservacion", required = false) String sort,
                                                                     @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Observacion> listObs = observacionServiceImp.getAllObservacion(personaId, pageRequest);
        PagedModel<Observacion> pagedObs = new PagedModel<>(listObs);
        return ResponseEntity.ok().body(pagedObs);
    }

    @PutMapping("/{observacionId}")
    public ResponseEntity<Observacion> putObservacion(@PathVariable Long personaId,
                                            @PathVariable Long alertaId,
                                            @PathVariable Long observacionId,
                                            @RequestBody Observacion observacion) {
        Observacion updatedObs =  observacionServiceImp.updateObservacion(personaId, observacionId, observacion);
        return ResponseEntity.ok().body(updatedObs);
    }

    @DeleteMapping("/{observacionId}")
    public ResponseEntity<Void> deleteObservacion(@PathVariable Long personaId,
                                                  @PathVariable Long alertaId,
                                                  @PathVariable Long observacionId) {
        observacionServiceImp.deleteObservacion(personaId, observacionId);
        return ResponseEntity.accepted().build();
    }
}
