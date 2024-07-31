package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.CoordDTO;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.services.CoordinadorServiceImp;
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
public class CoordinadorController {

    private final CoordinadorServiceImp coordinadorServiceImp;

    public CoordinadorController(CoordinadorServiceImp coordinadorServiceImp) {
        this.coordinadorServiceImp = coordinadorServiceImp;
    }

    @PostMapping("/persona/{personaId}/coordinador")
    public ResponseEntity<Persona> createCoord(@PathVariable Long personaId,
                                               @RequestBody CoordDTO reqBody) {
        Persona coord = coordinadorServiceImp.createCoord(personaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(coord.getIdCoordinador())
                        .toUri();
        return ResponseEntity.created(uri).body(coord);
    }

    @GetMapping("/coordinador/{coordinadorId}")
    public ResponseEntity<Persona> getCoordById(@PathVariable String coordinadorId) {
        Persona coord = coordinadorServiceImp.findCoordById(coordinadorId);
        return ResponseEntity.ok().body(coord);
    }

    @GetMapping("/coordinador")
    public ResponseEntity<PagedModel<Persona>> getAllCoord(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                           @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                           @RequestParam (value = "sort", defaultValue = "dniRieniec", required = false) String sort,
                                                           @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Persona> listCoord = coordinadorServiceImp.findAllCoord(pageRequest);
        PagedModel<Persona> pagedCoord = new PagedModel<>(listCoord);
        return ResponseEntity.ok().body(pagedCoord);
    }

    @PutMapping("/coordinador/{coordinadorId}")
    public ResponseEntity<Persona> putCoordById(@PathVariable String coordinadorId,
                                                @RequestBody CoordDTO adm) {
        Persona updatedCoord =  coordinadorServiceImp.updateCoord(coordinadorId, adm);
        return ResponseEntity.ok().body(updatedCoord);
    }

    @DeleteMapping("/coordinador/{coordinadorId}")
    public ResponseEntity<Void> deleteCoordById(@PathVariable String coordinadorId) {
        coordinadorServiceImp.deleteCoord(coordinadorId);
        return ResponseEntity.accepted().build();
    }
}
