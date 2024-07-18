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
public class AdministradorServiceImp {

    private final PersonaRepository personaRepository;

    public AdministradorServiceImp(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    @Transactional
    public Persona createAdm(Long personaId, Persona adm){
        Persona persona = validatePersonaId(personaId);
        persona.setIdAdministrador(adm.getIdAdministrador());
        persona.setCelular(adm.getCelular());
        persona.setEmailSecundario(adm.getEmailSecundario());
        persona.setRoleType(RoleType.ADMIN);
        return personaRepository.save(persona);
    }

    @Transactional(readOnly = true)
    public Persona findAdminById(Long personaId, Long admId) {
        validatePersonaId(personaId);
        return personaRepository.findByDniRieniecAndRoleType(admId, RoleType.ADMIN)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllAdm(Long personaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        Page<Persona> listAdm = personaRepository.findAllByRoleType(RoleType.ADMIN, pageRequest);
        return listAdm;
    }

    @Transactional
    public Persona updateAdm(Long personaId, Long admId, Persona adm){
        Persona persona = validatePersonaId(personaId);
        if(persona.getRoleType()!=RoleType.ADMIN){
            throw new NotFoundException("No dniAdministrador " + admId + " into the data base.");
        }
        persona.setIdAdministrador(adm.getIdAdministrador());
        persona.setCelular(adm.getCelular());
        persona.setEmailSecundario(adm.getEmailSecundario());
        return personaRepository.save(persona);
    }

    @Transactional
    public void deleteAdm(Long personaId, Long admId) {
        validatePersonaId(personaId);
        Persona adm = personaRepository.findById(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
        adm.setRoleType(RoleType.USER);
        personaRepository.save(adm);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
