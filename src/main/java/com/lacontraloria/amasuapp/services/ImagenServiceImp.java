package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.ImagenRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.domains.Imagen;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagenServiceImp {

    private final ImagenRepository imagenRepository;
    private final PersonaRepository personaRepository;

    public ImagenServiceImp(ImagenRepository imagenRepository, PersonaRepository personaRepository) {
        this.imagenRepository = imagenRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Imagen createImagen(Long personaId, MultipartFile img) throws IOException {
        validatePersonaId(personaId);
        Imagen teste = new Imagen();
        teste.setIdImagem(1L);
        teste.setImagen1(img.getBytes());
//        validateAlertaId(alertaId);
        return imagenRepository.save(teste);
    }
//
//    @Transactional(readOnly = true)
//    public Curso findByCursoId(Long personaId, Long cursoId) {
//        validatePersonaId(personaId);
//        return cursoRepository.findById(cursoId)
//                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
//    }
//
//    @Transactional(readOnly = true)
//    public Page<Curso> getAllCurso(Long personaId, PageRequest pageRequest) {
//        validatePersonaId(personaId);
//        Page<Curso> listCurso = cursoRepository.findAll(pageRequest);
//        return listCurso;
//    }
//
//    @Transactional
//    public Curso updateCurso(Long personaId, Long cursoId, Curso curso){
//        validatePersonaId(personaId);
//        cursoRepository.findById(curso.getIdCurso())
//                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
//        Curso updatedCurso = cursoRepository.save(curso);
//        return updatedCurso;
//    }
//
//    @Transactional
//    public void deleteCurso(Long personaId, Long cursoId) {
//        validatePersonaId(personaId);
//        Curso curso = cursoRepository.findById(cursoId)
//                .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
//        cursoRepository.delete(curso);
//    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }

    private Persona validateAlertaId(Long personaId) {
//        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
        return null;
    }
}
