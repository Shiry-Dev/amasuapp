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
public class CoordinadorServiceImp {

    private final PersonaRepository personaRepository;

    public CoordinadorServiceImp(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    @Transactional
    public Persona createCoord(Long personaId, Persona coord){
        Persona persona = validatePersonaId(personaId);
        persona.setIdCoordinador(coord.getIdCoordinador());
        persona.setCelular(coord.getCelular());
        persona.setEmailSecundario(coord.getEmailSecundario());
        persona.setRoleType(RoleType.COORD);
        return personaRepository.save(persona);
    }

    @Transactional(readOnly = true)
    public Persona findCoordById(Long personaId, Long coordId) {
        validatePersonaId(personaId);
        return personaRepository.findByDniRieniecAndRoleType(coordId, RoleType.COORD)
                .orElseThrow(() -> new NotFoundException("No dniCoordinador " + coordId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllCoord(Long personaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        Page<Persona> listCoord = personaRepository.findAllByRoleType(RoleType.COORD, pageRequest);
        return listCoord;
    }

    @Transactional
    public Persona updateCoord(Long personaId, Long coordId, Persona coord){
        Persona persona = validatePersonaId(personaId);
        if(persona.getRoleType()!=RoleType.COORD){
            throw new NotFoundException("No dniCoordinador " + coordId + " into the data base.");
        }
        persona.setIdCoordinador(coord.getIdCoordinador());
        persona.setCelular(coord.getCelular());
        persona.setEmailSecundario(coord.getEmailSecundario());
        return personaRepository.save(persona);
    }

    @Transactional
    public void deleteCoord(Long personaId, Long coordId) {
        validatePersonaId(personaId);
        Persona coord = personaRepository.findById(coordId)
                .orElseThrow(() -> new NotFoundException("No dniCoordinador " + coordId + " into the data base."));
        coord.setRoleType(RoleType.USER);
        personaRepository.save(coord);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
