package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.MonitorDTO;
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
@RequestMapping("/v1")
public class MonitorController {

    private final MonitorServiceImp monitorServiceImp;

    public MonitorController(MonitorServiceImp monitorServiceImp) {
        this.monitorServiceImp = monitorServiceImp;
    }

    @PostMapping("/persona/{personaId}/monitor")
    public ResponseEntity<Persona> createAdm(@PathVariable Long personaId,
                                             @RequestBody MonitorDTO reqBody) {
        Persona monitor = monitorServiceImp.createMonitor(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(monitor.getIdMonitor())
                        .toUri();
        return ResponseEntity.created(uri).body(monitor);
    }

    @GetMapping("/monitor/{monitorId}")
    public ResponseEntity<Persona> getMonitorById(@PathVariable String monitorId) {
        Persona monitor = monitorServiceImp.findMonitorById(monitorId);
        return ResponseEntity.ok().body(monitor);
    }

    @GetMapping("/monitor")
    public ResponseEntity<PagedModel<Persona>> getAllMonitor(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                             @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                             @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                             @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listMonitor = monitorServiceImp.findAllMonitor(pageRequest);
        PagedModel<Persona> pagedMonitor = new PagedModel<>(listMonitor);
        return ResponseEntity.ok().body(pagedMonitor);
    }

    @GetMapping("/monitor/distrito")
    public ResponseEntity<PagedModel<Persona>> getAllMonitorByDistrito(@RequestParam (value = "distritoActual") String distritoActual,
                                                                       @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                                       @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                                       @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                                       @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listMonitor = monitorServiceImp.findAllMonitorByDistrito(distritoActual, pageRequest);
        PagedModel<Persona> pagedMonitor = new PagedModel<>(listMonitor);
        return ResponseEntity.ok().body(pagedMonitor);
    }

    @PutMapping("/monitor/{monitorId}")
    public ResponseEntity<Persona> putMonitorById(@PathVariable String monitorId,
                                                  @RequestBody MonitorDTO monitor) {
        Persona updatedMonitor = monitorServiceImp.updateMonitor(monitorId, monitor);
        return ResponseEntity.ok().body(updatedMonitor);
    }

    @DeleteMapping("/monitor/{monitorId}")
    public ResponseEntity<Void> deleteMonitorById(@PathVariable String monitorId) {
        monitorServiceImp.deleteMonitor(monitorId);
        return ResponseEntity.accepted().build();
    }
}
