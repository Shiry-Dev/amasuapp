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
public class MonitorServiceImp {

    private final PersonaRepository personaRepository;

    public MonitorServiceImp(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }


    @Transactional
    public Persona createMonitor(Long personaId, Persona monitor){
        Persona persona = validatePersonaId(personaId);
        persona.setIdMonitor(monitor.getIdMonitor());
        persona.setAnioIngreso(monitor.getAnioIngreso());
        persona.setRegionInscription(monitor.getRegionInscription());
        persona.setProvinciaInscripcion(monitor.getProvinciaInscripcion());
        persona.setDistritoInscripcion(monitor.getDistritoInscripcion());
        persona.setUbigeoInei(monitor.getUbigeoInei());
        persona.setRegionActual(monitor.getRegionActual());
        persona.setProvinciaActual(monitor.getProvinciaActual());
        persona.setDistritoActual(monitor.getDistritoActual());
        persona.setEstudios(monitor.getEstudios());
        persona.setOcupacion(monitor.getOcupacion());
        persona.setCarrera(monitor.getCarrera());
        persona.setExperiencia(monitor.getExperiencia());
        persona.setProfesionDepurada(monitor.getProfesionDepurada());
        persona.setConvocatoriaIngreso(monitor.getConvocatoriaIngreso());
        persona.setEmailSecundario(monitor.getEmailSecundario());
        persona.setCelular(monitor.getCelular());
        persona.setRoleType(RoleType.MONITOR);
        return personaRepository.save(persona);
    }

    @Transactional(readOnly = true)
    public Persona findMonitorById(String monitorId) {
        return personaRepository.findPersonaByIdMonitor(monitorId)
                .orElseThrow(() -> new NotFoundException("No dniMonitor " + monitorId + " into the data base."));
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllMonitor(PageRequest pageRequest) {
        Page<Persona> listMonitor = personaRepository.findAllByRoleType(RoleType.MONITOR, pageRequest);
        return listMonitor;
    }

    @Transactional(readOnly = true)
    public Page<Persona> findAllMonitorByDistrito(String distrito, PageRequest pageRequest) {
        Page<Persona> listMonitor = personaRepository.findAllByRoleTypeAndDistritoActual(RoleType.MONITOR, distrito, pageRequest);
        return listMonitor;
    }

    @Transactional
    public Persona updateMonitor(String monitorId , Persona monitor){
        Persona persona = personaRepository.findPersonaByIdMonitor(monitorId)
                .orElseThrow(() -> new NotFoundException("No dniMonitor " + monitorId + " into the data base."));
        if(persona.getRoleType()!=RoleType.MONITOR){
            throw new NotFoundException("No dniMonitor " + monitorId + " into the data base.");
        }
        persona.setIdMonitor(monitorId);
        persona.setAnioIngreso(monitor.getAnioIngreso());
        persona.setRegionInscription(monitor.getRegionInscription());
        persona.setProvinciaInscripcion(monitor.getProvinciaInscripcion());
        persona.setDistritoInscripcion(monitor.getDistritoInscripcion());
        persona.setUbigeoInei(monitor.getUbigeoInei());
        persona.setRegionActual(monitor.getRegionActual());
        persona.setProvinciaActual(monitor.getProvinciaActual());
        persona.setDistritoActual(monitor.getDistritoActual());
        persona.setEstudios(monitor.getEstudios());
        persona.setOcupacion(monitor.getOcupacion());
        persona.setCarrera(monitor.getCarrera());
        persona.setExperiencia(monitor.getExperiencia());
        persona.setProfesionDepurada(monitor.getProfesionDepurada());
        persona.setConvocatoriaIngreso(monitor.getConvocatoriaIngreso());
        persona.setEmailSecundario(monitor.getEmailSecundario());
        persona.setCelular(monitor.getCelular());
        return personaRepository.save(persona);
    }

    @Transactional
    public void deleteMonitor(String monitorId) {
        Persona monitor = personaRepository.findPersonaByIdMonitor(monitorId)
                .orElseThrow(() -> new NotFoundException("No dniMonitor " + monitorId + " into the data base."));

        monitor.setIdMonitor(null);
        monitor.setAnioIngreso(null);
        monitor.setRegionInscription(null);
        monitor.setProvinciaInscripcion(null);
        monitor.setDistritoInscripcion(null);
        monitor.setUbigeoInei(null);
        monitor.setRegionActual(null);
        monitor.setProvinciaActual(null);
        monitor.setDistritoActual(null);
        monitor.setEstudios(null);
        monitor.setOcupacion(null);
        monitor.setCarrera(null);
        monitor.setExperiencia(null);
        monitor.setProfesionDepurada(null);
        monitor.setConvocatoriaIngreso(null);
        monitor.setEmailSecundario(null);
        monitor.setCelular(null);
        monitor.setRoleType(RoleType.USER);
        personaRepository.save(monitor);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }
}
