package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaDeleteService {

    private final PersonaRepository personaRepository;

    public PersonaDeleteService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public void deletePersona(Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new RuntimeException("No dniRienic " + personaId + " into the data base."));
        personaRepository.delete(persona);
    }
}
