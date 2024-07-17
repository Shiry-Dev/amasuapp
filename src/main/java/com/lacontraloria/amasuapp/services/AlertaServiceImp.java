package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AlertaServiceImp {

    private final AlertaRepository alertaRepository;
    private final PersonaRepository personaRepository;

    public AlertaServiceImp(AlertaRepository alertaRepository, PersonaRepository personaRepository) {
        this.alertaRepository = alertaRepository;
        this.personaRepository = personaRepository;
    }


    @Transactional
    public Alerta createAlerta(Long personaId, Alerta alerta){
        Persona persona = validatePersonaId(personaId);
        alerta.setFechaCreacion(LocalDateTime.now());
        alerta.setPersona(persona);
        return alertaRepository.save(alerta);
    }

    @Transactional(readOnly = true)
    public Alerta findByAlertaId(Long personaId, Long alertaId) {
        validatePersonaId(personaId);
        return alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Alerta> getAllAlerta(Long personaId, Long cui, PageRequest pageRequest) {
        validatePersonaId(personaId);
        if(cui == null){
            return alertaRepository.findAll(pageRequest);
        }else {
            return alertaRepository.findByCui(cui, pageRequest);
        }
    }

    @Transactional
    public Alerta updateAlerta(Long personaId, Long alertaId, Alerta reqAlerta){
        Persona persona = validatePersonaId(personaId);
        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
        reqAlerta.setIdAlerta(alerta.getIdAlerta());
        reqAlerta.setFechaCreacion(alerta.getFechaCreacion());
        reqAlerta.setPersona(persona);
        Alerta updatedAlerta = alertaRepository.save(reqAlerta);
        return updatedAlerta;
    }

    @Transactional
    public void deleteAlerta(Long personaId, Long alertaId) {
        validatePersonaId(personaId);
        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
        alertaRepository.delete(alerta);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
