package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.CursoRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.domains.Persona;
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
    public Curso createCurso(Long personaId, Curso curso){
        validatePersonaId(personaId);
        return cursoRepository.save(curso);
    }

    @Transactional(readOnly = true)
    public Curso findByCursoId(Long personaId, Long cursoId) {
        validatePersonaId(personaId);
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Curso> getAllCurso(Long personaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        Page<Curso> listCurso = cursoRepository.findAll(pageRequest);
        return listCurso;
    }

    @Transactional
    public Curso updateCurso(Long personaId, Long cursoId, Curso curso){
        validatePersonaId(personaId);
        cursoRepository.findById(curso.getIdCurso())
                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
        Curso updatedCurso = cursoRepository.save(curso);
        return updatedCurso;
    }

    @Transactional
    public void deleteCurso(Long personaId, Long cursoId) {
        validatePersonaId(personaId);
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
        cursoRepository.delete(curso);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
