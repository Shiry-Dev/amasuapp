package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.VeeduriaRepository;
import com.lacontraloria.amasuapp.domains.Persona;
import com.lacontraloria.amasuapp.domains.RoleType;
import com.lacontraloria.amasuapp.domains.Veeduria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VeeduriaServiceImp {

    @Autowired
    private VeeduriaRepository veeduriaRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
    public Veeduria createVeeduria(Veeduria veeduria) {
        veeduria.setFechaCreacion(LocalDateTime.now());
        return veeduriaRepository.save(veeduria);
    }

//    @Transactional
//    public Veeduria addMonitorsToVeeduria(Long veeduriaId, List<Long> monitorIds) {
//        Optional<Veeduria> veeduriaOptional = veeduriaRepository.findById(veeduriaId);
//        if (veeduriaOptional.isPresent()) {
//            Veeduria veeduria = veeduriaOptional.get();
//            Set<Persona> monitors = new HashSet<>();
//            for (Long monitorId : monitorIds) {
//                Persona persona = personaRepository.findById(monitorId).get();
//                if (persona.getRoleType() == RoleType.MONITOR) {
//                    monitors.add(persona);
//                }
//            }
//            veeduria.setCui(veeduria.getCui());
//            veeduria.setFechaCreacion(veeduria.getFechaCreacion());
//            veeduria.setFechaVeeduria(veeduria.getFechaVeeduria());
//            veeduria.setMonitoresAsignados(monitors);
//            return veeduriaRepository.save(veeduria);
//        } else {
//            throw new NotFoundException("Veeduria not found!");
//        }
//    }

    public Veeduria getVeeduriaById(Long veeduriaId) {
        return veeduriaRepository.findById(veeduriaId)
                .orElseThrow(() -> new NotFoundException("Veeduria not found!"));
    }

    public Page<Veeduria> getAllVeedurias(PageRequest pageRequest) {
        return veeduriaRepository.findAll(pageRequest);
    }

    public List<Persona> getMonitorsByVeeduriaId(Long veeduriaId) {
        Veeduria veeduria = veeduriaRepository.findById(veeduriaId)
                .orElseThrow(() -> new NotFoundException("Veeduria not found!"));
        return List.copyOf(veeduria.getMonitoresAsignados());
    }

    @Transactional
    public void postulateMonitorsToVeeduria(Long veeduriaId, List<Long> monitorIds) {
        Veeduria veeduria = veeduriaRepository.findById(veeduriaId)
                .orElseThrow(() -> new NotFoundException("Veeduria not found!"));
        Set<Persona> postulados = new HashSet<>();
        for (Long monitorId : monitorIds) {
            Persona persona = personaRepository.findById(monitorId)
                    .orElseThrow(() -> new NotFoundException("Monitor not found!"));
            if (persona.getRoleType() == RoleType.MONITOR) {
                postulados.add(persona);
            }
        }
        veeduria.setCui(veeduria.getCui());
        veeduria.setFechaCreacion(veeduria.getFechaCreacion());
        veeduria.setFechaVeeduria(veeduria.getFechaVeeduria());
        veeduria.setMonitoresPostulados(postulados);
        veeduriaRepository.save(veeduria);
    }

    @Transactional
    public void assignMonitorsToVeeduria(Long veeduriaId, List<Long> monitorIds) {
        Veeduria veeduria = veeduriaRepository.findById(veeduriaId)
                .orElseThrow(() -> new NotFoundException("Veeduria not found!"));

        Set<Persona> asignados = new HashSet<>();
        Set<Persona> postuladosAtualizados = new HashSet<>(veeduria.getMonitoresPostulados());

        for (Long monitorId : monitorIds) {
            Persona persona = personaRepository.findById(monitorId)
                    .orElseThrow(() -> new NotFoundException("Monitor not found!"));
            if (persona.getRoleType() == RoleType.MONITOR) {
                asignados.add(persona);
                postuladosAtualizados.remove(persona);
            }
        }

        veeduria.setMonitoresAsignados(asignados);
        veeduria.setMonitoresPostulados(postuladosAtualizados);
        veeduriaRepository.save(veeduria);
    }

    public void removeMonitorFromVeeduria(Long veeduriaId, Long monitorId) {
        Veeduria veeduria = veeduriaRepository.findById(veeduriaId)
                .orElseThrow(() -> new NotFoundException("Veeduria not found!"));
        Persona monitor = personaRepository.findById(monitorId)
                .orElseThrow(() -> new NotFoundException("Monitor not found!"));
        Set<Persona> monitores = new HashSet<>(veeduria.getMonitoresAsignados());
        monitores.remove(monitor);
        veeduria.setMonitoresAsignados(monitores);
        veeduriaRepository.save(veeduria);
    }

}
