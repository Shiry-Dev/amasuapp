package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.Veeduria;
import com.lacontraloria.amasuapp.services.VeeduriaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class VeeduriaController {

    @Autowired
    private VeeduriaServiceImp veeduriaServiceImp;

    @PostMapping("/persona/{personaId}/veeduria")
    public ResponseEntity<Veeduria> createVeeduria(@PathVariable Long personaId,
                                                   @RequestBody Veeduria veeduria) {
        Veeduria createdVeeduria = veeduriaServiceImp.createVeeduria(personaId, veeduria);
        return ResponseEntity.ok(createdVeeduria);
    }

    @GetMapping("/veedurias/{veeduriaId}")
    public ResponseEntity<Veeduria> getVeeduriaById(@PathVariable Long veeduriaId) {
        Veeduria veeduria = veeduriaServiceImp.getVeeduriaById(veeduriaId);
        return ResponseEntity.ok().body(veeduria);
    }

    @GetMapping("/veedurias/{veeduriaId}/monitores")
    public ResponseEntity<List<Persona>> getMonitorsByVeeduriaId(@PathVariable Long veeduriaId) {
        List<Persona> monitors = veeduriaServiceImp.getMonitorsByVeeduriaId(veeduriaId);
        return ResponseEntity.ok().body(monitors);
    }

    @PostMapping("/veedurias/{veeduriaId}/monitores/postulate")
    public ResponseEntity<Void> postulateMonitors(@PathVariable Long veeduriaId, @RequestBody List<Long> monitorIds) {
        veeduriaServiceImp.postulateMonitorsToVeeduria(veeduriaId, monitorIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/veedurias/{veeduriaId}/monitores/asignado")
    public ResponseEntity<Void> assignMonitors(@PathVariable Long veeduriaId, @RequestBody List<Long> monitorIds) {
        veeduriaServiceImp.assignMonitorsToVeeduria(veeduriaId, monitorIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/veedurias")
    public ResponseEntity<PagedModel<Veeduria>> getAllVeedurias(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                @RequestParam(value = "sort", defaultValue = "fechaVeeduria") String sort,
                                                @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Veeduria> veedurias = veeduriaServiceImp.getAllVeedurias(pageRequest);
        PagedModel<Veeduria> pagedVeedurias = new PagedModel<>(veedurias);
        return ResponseEntity.ok().body(pagedVeedurias);
    }

    @PutMapping("/veedurias/{veeduriaId}")
    public ResponseEntity<Veeduria> updateVeeduria(@PathVariable Long veeduriaId, @RequestBody Veeduria veeduria) {
        Veeduria updatedVeeduria = veeduriaServiceImp.updateVeeduria(veeduriaId, veeduria);
        return ResponseEntity.ok().body(updatedVeeduria);
    }


    @DeleteMapping("/veedurias/{veeduriaId}/monitores/postulado/{monitorId}")
    public ResponseEntity<Void> deleteMonitorPostulado(@PathVariable Long veeduriaId, @PathVariable Long monitorId) {
        veeduriaServiceImp.removeMonitorPostulado(veeduriaId, monitorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/veedurias/{veeduriaId}/monitores/asignado/{monitorId}")
    public ResponseEntity<Void> deleteMonitorFromVeeduria(@PathVariable Long veeduriaId, @PathVariable Long monitorId) {
        veeduriaServiceImp.removeMonitorFromVeeduria(veeduriaId, monitorId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/veedurias/{veeduriaId}")
    public ResponseEntity<Void> deleteVeeduria(@PathVariable Long veeduriaId) {
        veeduriaServiceImp.deleteVeeduria(veeduriaId);
        return ResponseEntity.ok().build();
    }

}