package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.domains.Imagen;
import com.lacontraloria.amasuapp.services.ImagenServiceImp;
import jdk.jfr.ContentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("v1/persona/{personaId}/imagen")
public class ImagenController {

    private final ImagenServiceImp imagenServiceImp;

    public ImagenController(ImagenServiceImp imagenServiceImp) {
        this.imagenServiceImp = imagenServiceImp;
    }

    @PostMapping
    public ResponseEntity<Imagen> createImagen(@PathVariable Long personaId,
                                               @RequestParam("imagen1") MultipartFile imagen1) throws IOException {
        Imagen imagen = imagenServiceImp.createImagen(personaId, imagen1);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(uri).body(imagen);
    }

//    @GetMapping("/{cursoId}")
//    public ResponseEntity<Curso> getCursoById(@PathVariable Long personaId,
//                                              @PathVariable Long cursoId) {
//        Curso curso = cursoServiceImp.findByCursoId(personaId, cursoId);
//        return ResponseEntity.ok().body(curso);
//    }
//
//    @GetMapping
//    public ResponseEntity<PagedModel<Curso>> getAllCurso(@PathVariable Long personaId,
//                                                         @RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
//                                                         @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
//                                                         @RequestParam (value = "sort", defaultValue = "idCurso", required = false) String sort,
//                                                         @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
//        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
//        Page<Curso> listCurso = cursoServiceImp.getAllCurso(personaId, pageRequest);
//        PagedModel<Curso> pagedPersona = new PagedModel<>(listCurso);
//        return ResponseEntity.ok().body(pagedPersona);
//    }
//
//    @PutMapping("/{cursoId}")
//    public ResponseEntity<Curso> putCursoId(@PathVariable Long personaId,
//                                            @PathVariable Long cursoId,
//                                            @RequestBody Curso curso) {
//        Curso updatedCurso =  cursoServiceImp.updateCurso(personaId, cursoId, curso);
//        return ResponseEntity.ok().body(updatedCurso);
//    }
//
//    @DeleteMapping("/{cursoId}")
//    public ResponseEntity<Void> deleteCurso(@PathVariable Long personaId,
//                                            @PathVariable Long cursoId) {
//        cursoServiceImp.deleteCurso(personaId, cursoId);
//        return ResponseEntity.accepted().build();
//    }
}
