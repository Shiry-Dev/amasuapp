package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaServiceImp {

    private final PersonaRepository personaRepository;

    public PersonaServiceImp(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona createPersona(Persona person){
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

    @Transactional
    public Persona updatePersona(Persona persona){
        personaRepository.findById(persona.getDniRieniec())
                .orElseThrow(() -> new NotFoundException("No dniRieniec " + persona.getDniRieniec() + " into the data base."));
        Persona updatedPersona = personaRepository.save(persona);
        return updatedPersona;
    }

    @Transactional
    public void deletePersona(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
        personaRepository.delete(persona);
    }
}
