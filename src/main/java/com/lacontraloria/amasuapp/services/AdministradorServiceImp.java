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
    public Persona findAdminById(String admId) {
//        validatePersonaId(personaId);
        return personaRepository.findPersonaByIdAdministrador(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllAdm(PageRequest pageRequest) {
        Page<Persona> listAdm = personaRepository.findAllByRoleType(RoleType.ADMIN, pageRequest);
        return listAdm;
    }

    @Transactional
    public Persona updateAdm(String admId, Persona adm){
        Persona persona = personaRepository.findPersonaByIdAdministrador(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
        if(persona.getRoleType()!=RoleType.ADMIN){
            throw new NotFoundException("No dniAdministrador " + admId + " into the data base.");
        }
        persona.setIdAdministrador(admId);
        persona.setCelular(adm.getCelular());
        persona.setEmailSecundario(adm.getEmailSecundario());
        return personaRepository.save(persona);
    }

    @Transactional
    public void deleteAdm(String admId) {
        Persona adm = personaRepository.findPersonaByIdAdministrador(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
        adm.setIdAdministrador(null);
        adm.setEmailSecundario(null);
        adm.setCelular(null);
        adm.setRoleType(RoleType.USER);
        personaRepository.save(adm);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
