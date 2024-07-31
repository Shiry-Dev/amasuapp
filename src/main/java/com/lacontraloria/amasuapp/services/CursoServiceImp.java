package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.CursoDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.CursoRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoServiceImp {

    private final CursoRepository cursoRepository;
    private final PersonaRepository personaRepository;

    public CursoServiceImp(CursoRepository cursoRepository,
                           PersonaRepository personaRepository) {
        this.cursoRepository = cursoRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Curso createCurso(CursoDTO reqCurso){
        Curso curso = new Curso();
        curso.setDescCurso(reqCurso.descCurso());
        return cursoRepository.save(curso);
    }

    @Transactional(readOnly = true)
    public Curso findByCursoId( Long cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Curso> getAllCurso(PageRequest pageRequest) {
        Page<Curso> listCurso = cursoRepository.findAll(pageRequest);
        return listCurso;
    }

    @Transactional
    public Curso updateCurso(Long cursoId, CursoDTO reqCurso){
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
        curso.setIdCurso(cursoId);
        curso.setDescCurso(reqCurso.descCurso());
        Curso updatedCurso = cursoRepository.save(curso);
        return updatedCurso;
    }

}
