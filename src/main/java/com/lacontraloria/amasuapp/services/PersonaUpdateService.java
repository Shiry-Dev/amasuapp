package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.stereotype.Service;

@Service
public class PersonaUpdateService {

    private final PersonaRepository personaRepository;

    public PersonaUpdateService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    public Persona updatePersona(Persona persona){
        personaRepository.findById(persona.getDniRieniec())
                .orElseThrow(() -> new RuntimeException("No dniRieniec " + persona.getDniRieniec() + " into the data base."));
        Persona updatedPersona = personaRepository.save(persona);
        return updatedPersona;
    }
}
