package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.AuthDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.CursoRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Curso;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PersonaServiceImp {

    private final PersonaRepository personaRepository;
    private final CursoRepository cursoRepository;
    private final AlertaRepository alertaRepository;

    public PersonaServiceImp(PersonaRepository personaRepository, CursoRepository cursoRepository, AlertaRepository alertaRepository) {
        this.personaRepository = personaRepository;
        this.cursoRepository = cursoRepository;
        this.alertaRepository = alertaRepository;
    }




    @Transactional
    public Persona createPersona(Persona person){
        personaRepository.findById(person.getDniRieniec())
                .ifPresent(persona -> {
                    throw new IllegalArgumentException("dniRieniec " + person.getDniRieniec() + " already exists.");
                });
        personaRepository.findByEmailPrincipal(person.getEmailPrincipal())
                .ifPresent(persona -> {
                    throw new IllegalArgumentException("emailPrincipal " + person.getEmailPrincipal() + " already exists.");
                });

//        String encryptedPassword = new BCryptPasswordEncoder().encode(person.getPassword());
//        person.setPassword(encryptedPassword);
        person.setRoleType(RoleType.USER);
        return personaRepository.save(person);
    }

    @Transactional(readOnly = true)
    public Persona findPersonaByDniReniec(Long personaId) {
        return personaRepository.findByDniRieniecAndRoleType(personaId, RoleType.USER)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> getAllPersona(PageRequest pageRequest) {
        Page<Persona> listPersona = personaRepository.findAll(pageRequest);
        return listPersona;
    }

    @Transactional(readOnly = true)
    public Page<Persona> getAllUserPersona(PageRequest pageRequest) {
        Page<Persona> listPersona = personaRepository.findAllByRoleType(RoleType.USER, pageRequest);
        return listPersona;
    }

    @Transactional
    public Persona updatePersona(Long personaId, Persona persona){
        Persona validPersona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRieniec " + persona.getDniRieniec() + " into the data base."));
        validPersona.setPaterno(persona.getPaterno());
        validPersona.setMaterno(persona.getMaterno());
        validPersona.setNombres(persona.getNombres());
        validPersona.setFechaNacimiento(persona.getFechaNacimiento());
        validPersona.setEmailPrincipal(persona.getEmailPrincipal());
        validPersona.setPassword(persona.getPassword());
        Persona updatedPersona = personaRepository.save(validPersona);
        return updatedPersona;
    }

    @Transactional
    public void deletePersona(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
        personaRepository.delete(persona);
    }

    public void assignCursoToPersona(Long personaId, List<Long> cursoIds) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
        Set<Curso> asignados = new HashSet<>(persona.getCursos());

        for (Long cursoId : cursoIds) {
            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new NotFoundException("No cursoId " + cursoId + " into the data base."));
            asignados.add(curso);
        }
        persona.setCursos(asignados);
        personaRepository.save(persona);
    }

//    public void assignAlertaToPersona(Long personaId, List<Long> alertaIds) {
//        Persona persona = personaRepository.findById(personaId)
//                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
//        Set<Alerta> asignados = new HashSet<>(persona.getAlertas());
//
//        for (Long alertaId: alertaIds) {
//            Alerta alerta = alertaRepository.findById(alertaId)
//                    .orElseThrow(() -> new NotFoundException("No alertaId" + alertaId + " into the data base."));
//            asignados.add(alerta);
//        }
//        persona.setAlertas(asignados);
//        personaRepository.save(persona);
//    }

    @Transactional
    public void assignAlertaToPersona(Long personaId, List<Long> alertaIds) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " found in the database."));
        Set<Alerta> asignados = new HashSet<>(persona.getAlertas());

        for (Long alertaId : alertaIds) {
            Alerta alerta = alertaRepository.findById(alertaId)
                    .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " found in the database."));
            asignados.add(alerta);
            alerta.getPersonas().add(persona);
        }
        persona.setAlertas(asignados);
        personaRepository.save(persona);
    }

}
