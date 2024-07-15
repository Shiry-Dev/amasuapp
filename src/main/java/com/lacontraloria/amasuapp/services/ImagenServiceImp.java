package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.ImagenDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.ImagenRepository;
import com.lacontraloria.amasuapp.adapters.repositories.PersonaRepository;
import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Imagen;
import com.lacontraloria.amasuapp.domains.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImagenServiceImp {

    private final ImagenRepository imagenRepository;
    private final PersonaRepository personaRepository;
    private final AlertaRepository alertaRepository;

    public ImagenServiceImp(ImagenRepository imagenRepository, PersonaRepository personaRepository, AlertaRepository alertaRepository) {
        this.imagenRepository = imagenRepository;
        this.personaRepository = personaRepository;
        this.alertaRepository = alertaRepository;
    }

    @Transactional
    public ImagenDTO createImagen(Long personaId, Long alertaId, Imagen reqBody) {
        Persona persona = validatePersonaId(personaId);
        Alerta alerta = validateAlertaId(alertaId);
        alerta.setPersona(persona);
        reqBody.setAlerta(alerta);
        imagenRepository.save(reqBody);
        return convertToDto(reqBody);
    }

    @Transactional(readOnly = true)
    public ImagenDTO findByImagenId(Long personaId, Long alertaId, Long imagenId) {
        validatePersonaId(personaId);
        validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        return convertToDto(imagen);
    }

    @Transactional(readOnly = true)
    public Page<ImagenDTO> getAllImagen(Long personaId, Long alertaId, PageRequest pageRequest) {
        validatePersonaId(personaId);
        validateAlertaId(alertaId);
        Page<Imagen> listImg = imagenRepository.findAll(pageRequest);
        return listImg.map(this::convertToDto);
    }

    @Transactional
    public ImagenDTO updateImagen(Long personaId, Long alertaId, Long imagenId, Imagen reqBody){
        Persona persona = validatePersonaId(personaId);
        Alerta alerta = validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        alerta.setPersona(persona);
        reqBody.setAlerta(alerta);
        reqBody.setIdImagen(imagen.getIdImagen());
        imagenRepository.save(reqBody);
        return convertToDto(reqBody);
    }

    @Transactional
    public void deleteImagen(Long personaId, Long alertaId, Long imagenId) {
        validatePersonaId(personaId);
        validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        imagenRepository.delete(imagen);
    }

    private Persona validatePersonaId(Long personaId) {
        return personaRepository.findById(personaId).orElseThrow(() -> new NotFoundException("No dniRienic " + personaId + " into the data base."));
    }

    private Alerta validateAlertaId(Long alertaId) {
        return alertaRepository.findById(alertaId).orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
    }


    private ImagenDTO convertToDto(Imagen imagen) {
        return new ImagenDTO(imagen.getIdImagen(), imagen.getImagen1(), imagen.getImagen2(), imagen.getImagen3(), imagen.getImagen4());
    }
}
