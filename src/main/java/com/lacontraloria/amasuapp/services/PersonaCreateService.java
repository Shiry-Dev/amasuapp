package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonaCreateService {

    private final PersonaRepository personaRepository;

    public PersonaCreateService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Persona createPersona(Persona person){
        return personaRepository.save(person);
    }
}
