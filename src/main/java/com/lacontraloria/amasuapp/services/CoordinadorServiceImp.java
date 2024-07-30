package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.CoordDTO;
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
    public Persona createCoord(Long personaId, CoordDTO coord){
        Persona persona = validatePersonaId(personaId);
        persona.setIdCoordinador(coord.idCoordinador());
        persona.setCelular(coord.celular());
        persona.setEmailSecundario(coord.emailSecundario());
        persona.setRoleType(RoleType.COORD);
        return personaRepository.save(persona);
    }

    @Transactional(readOnly = true)
    public Persona findCoordById(String coordId) {
//        validatePersonaId(personaId);
        return personaRepository.findPersonaByIdCoordinador(coordId)
                .orElseThrow(() -> new NotFoundException("No dniCoordinador " + coordId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllCoord(PageRequest pageRequest) {
        Page<Persona> listCoord = personaRepository.findAllByRoleType(RoleType.COORD, pageRequest);
        return listCoord;
    }

    @Transactional
    public Persona updateCoord(String coordId, CoordDTO coord){
        Persona persona = personaRepository.findPersonaByIdCoordinador(coordId)
                .orElseThrow(() -> new NotFoundException("No dniCoordinador " + coordId + " into the data base."));
        if(persona.getRoleType()!=RoleType.COORD){
            throw new NotFoundException("No dniCoordinador " + coordId + " into the data base.");
        }
        persona.setIdCoordinador(coordId);
        persona.setCelular(coord.celular());
        persona.setEmailSecundario(coord.emailSecundario());
        return personaRepository.save(persona);
    }

    @Transactional
    public void deleteCoord(String coordId) {
        Persona coord = personaRepository.findPersonaByIdCoordinador(coordId)
                .orElseThrow(() -> new NotFoundException("No dniCoordinador " + coordId + " into the data base."));
        coord.setIdCoordinador(null);
        coord.setEmailSecundario(null);
        coord.setCelular(null);
        coord.setRoleType(RoleType.USER);
        personaRepository.save(coord);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
