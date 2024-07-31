package com.lacontraloria.amasuapp.adapters.controllers;

import com.lacontraloria.amasuapp.adapters.dto.CursoDTO;
import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.services.CursoServiceImp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("v1/curso")
public class CursoController {

    private final CursoServiceImp cursoServiceImp;

    public CursoController(CursoServiceImp cursoServiceImp) {
        this.cursoServiceImp = cursoServiceImp;
    }

    @PostMapping
    public ResponseEntity<Curso> createCurso(@RequestBody CursoDTO reqBody) {
        Curso curso = cursoServiceImp.createCurso(reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(curso.getIdCurso())
                .toUri();
        return ResponseEntity.created(uri).body(curso);
    }

    @GetMapping("/{cursoId}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long cursoId) {
        Curso curso = cursoServiceImp.findByCursoId(cursoId);
        return ResponseEntity.ok().body(curso);
    }

    @GetMapping
    public ResponseEntity<PagedModel<Curso>> getAllCurso(@RequestParam (value = "page", defaultValue = "1", required = false) Integer page,
                                                         @RequestParam (value = "size", defaultValue = "10", required = false) Integer size,
                                                         @RequestParam (value = "sort", defaultValue = "idCurso", required = false) String sort,
                                                         @RequestParam (value = "direction", defaultValue = "ASC", required = false) String direction){
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.Direction.valueOf(direction), sort);
        Page<Curso> listCurso = cursoServiceImp.getAllCurso(pageRequest);
        PagedModel<Curso> pagedPersona = new PagedModel<>(listCurso);
        return ResponseEntity.ok().body(pagedPersona);
    }

    @PutMapping("/{cursoId}")
    public ResponseEntity<Curso> putCursoId(@PathVariable Long cursoId,
                                            @RequestBody CursoDTO curso) {
        Curso updatedCurso =  cursoServiceImp.updateCurso(cursoId, curso);
        return ResponseEntity.ok().body(updatedCurso);
    }

}
