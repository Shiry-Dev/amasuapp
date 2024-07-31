package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.ImagenDTO;
import com.lacontraloria.amasuapp.domains.Imagen;
import com.lacontraloria.amasuapp.services.ImagenServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/alerta/{alertaId}/imagen")
public class ImagenController {

    private final ImagenServiceImp imagenServiceImp;

    public ImagenController(ImagenServiceImp imagenServiceImp) {
        this.imagenServiceImp = imagenServiceImp;
    }

    @PostMapping
    public ResponseEntity<ImagenDTO> createImagen(@PathVariable Long alertaId,
                                                  @RequestBody Imagen reqBody) {
        ImagenDTO imagen = imagenServiceImp.createImagen(alertaId, reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(imagen.idImagen())
                .toUri();
        return ResponseEntity.created(uri).body(imagen);
    }

    @GetMapping("/{imagenId}")
    public ResponseEntity<ImagenDTO> getImgById(@PathVariable Long alertaId,
                                                @PathVariable Long imagenId) {
        ImagenDTO imagen = imagenServiceImp.findByImagenId(alertaId, imagenId);
        return ResponseEntity.ok().body(imagen);
    }

    @PutMapping("/{imagenId}")
    public ResponseEntity<ImagenDTO> putCursoId(@PathVariable Long alertaId,
                                                @PathVariable Long imagenId,
                                                @RequestBody Imagen reqBody) {
        ImagenDTO updatedImg =  imagenServiceImp.updateImagen(alertaId, imagenId, reqBody);
        return ResponseEntity.ok().body(updatedImg);
    }

    @DeleteMapping("/{imagenId}")
    public ResponseEntity<Void> deleteImagen(@PathVariable Long alertaId,
                                             @PathVariable Long imagenId) {
        imagenServiceImp.deleteImagen(alertaId, imagenId);
        return ResponseEntity.accepted().build();
    }
}
