package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AdminstradorRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Administrador;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdministradorServiceImp {

    private final AdminstradorRepository adminstradorRepository;
    private final PersonaRepository personaRepository;

    public AdministradorServiceImp(AdminstradorRepository adminstradorRepository,
                                   PersonaRepository personaRepository) {
        this.adminstradorRepository = adminstradorRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Administrador createAdm(Long personaId, Administrador adm){
        Persona persona = validatePersonaId(personaId);
        adm.setPersona(persona);
        adm.setDniAdministrador(persona.getDniRieniec());
        return adminstradorRepository.save(adm);
    }

    @Transactional(readOnly = true)
    public Administrador findAdminById(Long personaId, Long admId) {
        validatePersonaId(personaId);
        return adminstradorRepository.findById(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Administrador> findAllAdm(Long personaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        Page<Administrador> listAdm = adminstradorRepository.findAll(pageRequest);
        return listAdm;
    }

    @Transactional
    public Administrador updateAdm(Long personaId, Long admId, Administrador adm){
        Persona persona = validatePersonaId(personaId);
        adminstradorRepository.findById(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
        adm.setPersona(persona);
        adm.setDniAdministrador(persona.getDniRieniec());
        return adminstradorRepository.save(adm);
    }

    @Transactional
    public void deleteAdm(Long personaId, Long admId) {
        validatePersonaId(personaId);
        Administrador adm = adminstradorRepository.findById(admId)
                .orElseThrow(() -> new NotFoundException("No dniAdministrador " + admId + " into the data base."));
        adminstradorRepository.delete(adm);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
