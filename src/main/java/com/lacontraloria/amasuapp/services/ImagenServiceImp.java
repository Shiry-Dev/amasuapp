package com.lacontraloria.amasuapp.services;

import com.lacontraloria.amasuapp.adapters.dto.ImagenDTO;
import com.lacontraloria.amasuapp.adapters.exceptions.NotFoundException;
import com.lacontraloria.amasuapp.adapters.repositories.AlertaRepository;
import com.lacontraloria.amasuapp.adapters.repositories.ImagenRepository;
import com.lacontraloria.amasuapp.domains.Alerta;
import com.lacontraloria.amasuapp.domains.Imagen;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImagenServiceImp {

    private final ImagenRepository imagenRepository;
    private final AlertaRepository alertaRepository;

    public ImagenServiceImp(ImagenRepository imagenRepository, AlertaRepository alertaRepository) {
        this.imagenRepository = imagenRepository;
        this.alertaRepository = alertaRepository;
    }

    @Transactional
    public ImagenDTO createImagen(Long alertaId, Imagen reqBody) {
        Alerta alerta = validateAlertaId(alertaId);
        reqBody.setAlerta(alerta);
        imagenRepository.save(reqBody);
        return convertToDto(reqBody);
    }

    @Transactional(readOnly = true)
    public ImagenDTO findByImagenId( Long alertaId, Long imagenId) {
        validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        return convertToDto(imagen);
    }

//    @Transactional(readOnly = true)
//    public Page<ImagenDTO> getAllImagen( Long alertaId, PageRequest pageRequest) {
//        validateAlertaId(alertaId);
//        Page<Imagen> listImg = imagenRepository.findAll(pageRequest);
//        return listImg.map(this::convertToDto);
//    }

    @Transactional
    public ImagenDTO updateImagen( Long alertaId, Long imagenId, Imagen reqBody){
        Alerta alerta = validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        reqBody.setAlerta(alerta);
        reqBody.setIdImagen(imagen.getIdImagen());
        imagenRepository.save(reqBody);
        return convertToDto(reqBody);
    }

    @Transactional
    public void deleteImagen(Long alertaId, Long imagenId) {
        Alerta alerta = validateAlertaId(alertaId);
        Imagen imagen = imagenRepository.findById(imagenId)
                .orElseThrow(() -> new NotFoundException("No imagenId " + imagenId + " into the data base."));
        alerta.setImagen(null);
        alertaRepository.save(alerta);
        imagenRepository.delete(imagen);
    }

    private Alerta validateAlertaId(Long alertaId) {
        return alertaRepository.findById(alertaId).orElseThrow(() -> new NotFoundException("No alertaId " + alertaId + " into the data base."));
    }


    private ImagenDTO convertToDto(Imagen imagen) {
        return new ImagenDTO(imagen.getIdImagen(), imagen.getImagen1(), imagen.getImagen2(), imagen.getImagen3(), imagen.getImagen4());
    }
}
