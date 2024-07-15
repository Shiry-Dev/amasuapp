package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.ImagenDTO;
import com.lacontraloria.amasuapp.domains.Imagen;
import com.lacontraloria.amasuapp.services.ImagenServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/alerta/{alertaId}/imagen")
public class ImagenController {

    private final ImagenServiceImp imagenServiceImp;

    public ImagenController(ImagenServiceImp imagenServiceImp) {
        this.imagenServiceImp = imagenServiceImp;
    }

    @PostMapping
    public ResponseEntity<ImagenDTO> createImagen(@PathVariable Long personaId,
                                               @PathVariable Long alertaId,
                                               @RequestBody Imagen reqBody) {
        ImagenDTO imagen = imagenServiceImp.createImagen(personaId, alertaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(imagen.idImagen())
                .toUri();
        return ResponseEntity.created(uri).body(imagen);
    }

    @GetMapping("/{imagenId}")
    public ResponseEntity<ImagenDTO> getImgById(@PathVariable Long personaId,
                                                @PathVariable Long alertaId,
                                                @PathVariable Long imagenId) {
        ImagenDTO imagen = imagenServiceImp.findByImagenId(personaId, alertaId, imagenId);
        return ResponseEntity.ok().body(imagen);
    }

    @GetMapping
    public ResponseEntity<PagedModel<ImagenDTO>> getAllImagen(@PathVariable Long personaId,
                                                           @PathVariable Long alertaId,
                                                           @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                           @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                           @RequestParam (value = "sort", defaultValue = "idImagen", required = false) String sort,
                                                           @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<ImagenDTO> listImg = imagenServiceImp.getAllImagen(personaId, alertaId, pageRequest);
        PagedModel<ImagenDTO> pagedImg = new PagedModel<>(listImg);
        return ResponseEntity.ok().body(pagedImg);
    }

    @PutMapping("/{imagenId}")
    public ResponseEntity<ImagenDTO> putCursoId(@PathVariable Long personaId,
                                                @PathVariable Long alertaId,
                                                @PathVariable Long imagenId,
                                                @RequestBody Imagen reqBody) {
        ImagenDTO updatedImg =  imagenServiceImp.updateImagen(personaId, alertaId, imagenId, reqBody);
        return ResponseEntity.ok().body(updatedImg);
    }

    @DeleteMapping("/{imagenId}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long personaId,
                                             @PathVariable Long alertaId,
                                             @PathVariable Long imagenId) {
        imagenServiceImp.deleteImagen(personaId, alertaId, imagenId);
        return ResponseEntity.accepted().build();
    }
}
