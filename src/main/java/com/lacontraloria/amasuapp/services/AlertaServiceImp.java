package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.AlertaDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.ObservacionRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Observacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AlertaServiceImp {

    private final AlertaRepository alertaRepository;
    private final PersonaRepository personaRepository;
    private final ObservacionRepository observacionRepository;

    public AlertaServiceImp(AlertaRepository alertaRepository, PersonaRepository personaRepository, ObservacionRepository observacionRepository) {
        this.alertaRepository = alertaRepository;
        this.personaRepository = personaRepository;
        this.observacionRepository = observacionRepository;
    }


    @Transactional
    public Alerta createAlerta(AlertaDTO alertaReq){
        Alerta alerta = new Alerta();
        alerta.setCui(alertaReq.cui());
        alerta.setComentarioAlerta(alertaReq.comentarioAlerta());
        alerta.setFechaCreacion(LocalDateTime.now());
        return alertaRepository.save(alerta);
    }

    @Transactional(readOnly = true)
    public Alerta findByAlertaId(Long alertaId) {
        return alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Alerta> getAllAlerta(Long cui, PageRequest pageRequest) {
        if(cui == null){
            return alertaRepository.findAll(pageRequest);
        }else {
            return alertaRepository.findByCui(cui, pageRequest);
        }
    }

    @Transactional
    public Alerta updateAlerta(Long alertaId, Alerta reqAlerta){
        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
        reqAlerta.setIdAlerta(alerta.getIdAlerta());
        reqAlerta.setFechaCreacion(alerta.getFechaCreacion());
        Alerta updatedAlerta = alertaRepository.save(reqAlerta);
        return updatedAlerta;
    }

    @Transactional
    public void deleteAlerta(Long alertaId) {
        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
        alertaRepository.delete(alerta);
    }

    public void assignObservacionToAlerta(Long alertaId, List<Long> obsIds) {
        Alerta alerta = alertaRepository.findById(alertaId)
                .orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " found in the database."));
        Set<Observacion> asignados = new HashSet<>(alerta.getObservaciones());

        for (Long obsId : obsIds) {
            Observacion observacion = observacionRepository.findById(obsId)
                    .orElseThrow(() -> new NotFoundException("No observacionId " + obsId + " found in the database."));
            asignados.add(observacion);
            observacion.getAlertas().add(alerta);
        }
        alerta.setObservaciones(asignados);
        alertaRepository.save(alerta);
    }
}
