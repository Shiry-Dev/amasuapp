package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.CoordinadorServiceImp;
import com.lacontraloria.amasuapp.services.MonitorServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/monitor")
public class MonitorController {

    private final MonitorServiceImp monitorServiceImp;

    public MonitorController(MonitorServiceImp monitorServiceImp) {
        this.monitorServiceImp = monitorServiceImp;
    }

    @PostMapping
    public ResponseEntity<Persona> createAdm(@PathVariable Long personaId,
                                             @RequestBody Persona reqBody) {
        Persona monitor = monitorServiceImp.createMonitor(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(monitor.getIdMonitor())
                        .toUri();
        return ResponseEntity.created(uri).body(monitor);
    }

    @GetMapping("/{monitorId}")
    public ResponseEntity<Persona> getMonitorById(@PathVariable Long personaId,
                                                  @PathVariable Long monitorId) {
        Persona monitor = monitorServiceImp.findMonitorById(personaId, monitorId);
        return ResponseEntity.ok().body(monitor);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Persona>> getAllMonitor(@PathVariable Long personaId,
                                                             @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                             @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                             @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                             @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listMonitor = monitorServiceImp.findAllMonitor(personaId, pageRequest);
        PagedModel<Persona> pagedMonitor = new PagedModel<>(listMonitor);
        return ResponseEntity.ok().body(pagedMonitor);
    }

    @GetMapping("/distrito")
    public ResponseEntity<PagedModel<Persona>> getAllMonitorByDistrito(@PathVariable Long personaId,
                                                                       @RequestParam (value = "distritoActual") String distritoActual,
                                                                       @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                                       @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                                       @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                                       @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listMonitor = monitorServiceImp.findAllMonitorByDistrito(personaId, distritoActual, pageRequest);
        PagedModel<Persona> pagedMonitor = new PagedModel<>(listMonitor);
        return ResponseEntity.ok().body(pagedMonitor);
    }

    @PutMapping("/{monitorId}")
    public ResponseEntity<Persona> putPersonalById(@PathVariable Long personaId,
                                                   @PathVariable Long monitorId,
                                                   @RequestBody Persona adm) {
        Persona updatedMonitor = monitorServiceImp.updateMonitor(personaId, monitorId, adm);
        return ResponseEntity.ok().body(updatedMonitor);
    }

    @DeleteMapping("/{monitorId}")
    public ResponseEntity<Void> deletePersonaById(@PathVariable Long personaId,
                                                  @PathVariable Long monitorId) {
        monitorServiceImp.deleteMonitor(personaId, monitorId);
        return ResponseEntity.accepted().build();
    }
}
