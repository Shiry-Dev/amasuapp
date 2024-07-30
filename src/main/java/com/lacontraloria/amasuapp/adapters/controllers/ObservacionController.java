package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.ObservacionDTO;
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
@RequestMapping("v1/observacion")
public class ObservacionController {

    private final ObservacionServiceImp observacionServiceImp;

    public ObservacionController(ObservacionServiceImp observacionServiceImp) {
        this.observacionServiceImp = observacionServiceImp;
    }

    @PostMapping
    public ResponseEntity<Observacion> createObservacion(@RequestBody ObservacionDTO reqBody) {
        Observacion observacion = observacionServiceImp.createObservacion(reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(observacion.getIdObservacion())
                .toUri();
        return ResponseEntity.created(uri).body(observacion);
    }

    @GetMapping("/{observacionId}")
    public ResponseEntity<Observacion> getObservacion(@PathVariable Long observacionId) {
        Observacion observacion = observacionServiceImp.findByObservacion(observacionId);
        return ResponseEntity.ok().body(observacion);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Observacion>> getAllObservacion(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                                     @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                                     @RequestParam (value = "sort", defaultValue = "idObservacion", required = false) String sort,
                                                                     @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Observacion> listObs = observacionServiceImp.getAllObservacion(pageRequest);
        PagedModel<Observacion> pagedObs = new PagedModel<>(listObs);
        return ResponseEntity.ok().body(pagedObs);
    }

    @PutMapping("/{observacionId}")
    public ResponseEntity<Observacion> putObservacion(@PathVariable Long observacionId,
                                                      @RequestBody ObservacionDTO observacion) {
        Observacion updatedObs =  observacionServiceImp.updateObservacion(observacionId, observacion);
        return ResponseEntity.ok().body(updatedObs);
    }

    @DeleteMapping("/{observacionId}")
    public ResponseEntity<Void> deleteObservacion(@PathVariable Long observacionId) {
        observacionServiceImp.deleteObservacion(observacionId);
        return ResponseEntity.accepted().build();
    }
}
