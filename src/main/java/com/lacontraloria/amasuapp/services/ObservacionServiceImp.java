package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.ObservacionDTO;
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
    public Observacion createObservacion(ObservacionDTO observacion){
        Observacion obs = new Observacion();
        obs.setDescObservacion(observacion.descObservacion());
        return observacionRepository.save(obs);
    }

    @Transactional(readOnly = true)
    public Observacion findByObservacion(Long observacionId) {
        return observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Observacion> getAllObservacion(PageRequest pageRequest) {
        Page<Observacion> listObs = observacionRepository.findAll(pageRequest);
        return listObs;
    }

    @Transactional
    public Observacion updateObservacion(Long observacionId, ObservacionDTO observacion){
        Observacion obs = observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " into the data base."));
        obs.setDescObservacion(observacion.descObservacion());
        Observacion updatedObs = observacionRepository.save(obs);
        return updatedObs;
    }

    @Transactional
    public void deleteObservacion(Long observacionId) {
        Observacion observacion = observacionRepository.findById(observacionId)
                .orElseThrow(() -> new NotFoundException("No observacionId " + observacionId + " found in the database."));

        for (Alerta alerta : observacion.getAlertas()) {
            alerta.getObservaciones().remove(observacion);
            alertaRepository.save(alerta);
        }
        observacion.getAlertas().clear();

        observacionRepository.delete(observacion);
    }

}
