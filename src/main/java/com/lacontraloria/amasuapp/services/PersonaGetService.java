package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PersonaGetService {

    private final PersonaRepository personaRepository;

    public PersonaGetService(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Transactional(readOnly = true)
    public Persona findPersonaByDniReniec(Long personaId) {
        return personaRepository.findById(personaId)
                .orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
