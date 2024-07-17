package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.ObservacionRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservacionServiceImp {

    private final ObservacionRepository observacionRepository;
    private final AlertaRepository alertaRepository;
    private final PersonaRepository personaRepository;

    public ObservacionServiceImp(ObservacionRepository observacionRepository, AlertaRepository alertaRepository, PersonaRepository personaRepository) {
        this.observacionRepository = observacionRepository;
        this.alertaRepository = alertaRepository;
        this.personaRepository = personaRepository;
    }

    @Transactional
    public Observacion createObservacion(Long personaId, Long alertaId, Observacion observacion){
        validatePersonaId(personaId);
        observacion.setAlerta(validateAlertaId(alertaId));
        return observacionRepository.save(observacion);
    }

    @Transactional(readOnly = true)
    public Observacion findByObservacion(Long personaId, Long observacionId) {
        validatePersonaId(personaId);
        return observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Observacion> getAllObservacion(Long personaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        Page<Observacion> listObs = observacionRepository.findAll(pageRequest);
        return listObs;
    }

    @Transactional
    public Observacion updateObservacion(Long personaId, Long alertaId, Long observacionId, Observacion observacion){
        validatePersonaId(personaId);
        Alerta alerta = validateAlertaId(alertaId);
        Observacion obs = observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " into the data base."));
        observacion.setIdObservacion(obs.getIdObservacion());
        observacion.setAlerta(alerta);
        Observacion updatedObs = observacionRepository.save(observacion);
        return updatedObs;
    }

    @Transactional
    public void deleteObservacion(Long personaId, Long observacionId) {
        validatePersonaId(personaId);
        Observacion observacion = observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " into the data base."));
        observacionRepository.delete(observacion);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }

    private Alerta validateAlertaId(Long alertaId) {
        return alertaRepository.findById(alertaId).orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
    }
}
